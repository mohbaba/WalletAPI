package com.example.api.application.services;

import com.example.api.application.ports.input.walletUsecases.CreateWalletUseCase;
import com.example.api.application.ports.input.walletUsecases.DepositUseCase;
import com.example.api.application.ports.input.walletUsecases.GetBalanceUseCase;
import com.example.api.application.ports.input.walletUsecases.GetWalletUseCase;
import com.example.api.application.ports.output.TransactionOutputPort;
import com.example.api.application.ports.output.UserOutputPort;
import com.example.api.application.ports.output.WalletOutputPort;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackVerificationResponse;
import com.example.api.domain.exceptions.PasswordMismatchException;
import com.example.api.domain.exceptions.UserNotFoundException;
import com.example.api.domain.exceptions.WalletNotFoundException;
import com.example.api.domain.models.*;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.DepositRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AcceptPaymentRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackPaymentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

@RequiredArgsConstructor
public class WalletService implements CreateWalletUseCase, DepositUseCase, GetBalanceUseCase, GetWalletUseCase {
    private final WalletOutputPort walletOutputPort;
    private final UserOutputPort userOutputPort;
    private final TransactionOutputPort transactionOutputPort;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Wallet createWalletFor(String email) {
        User user = getUserByEmail(email);
        Wallet wallet = walletOutputPort.saveWallet(new Wallet());
        user.setWallet(wallet);
        userOutputPort.saveUser(user);
        return wallet;
    }

    @Override
    public PaystackPaymentResponse initializeDeposit(DepositRequest request) {
        if (!verifyUser(request.getEmailAddress(), request.getPassword())) {
            throw new PasswordMismatchException("Invalid username or password");
        }

        AcceptPaymentRequest paymentRequest = new AcceptPaymentRequest(request.getEmailAddress(), request.getAmount().toString());

        return paymentService.initializePayment(paymentRequest);
    }

    @Override
    @Transactional
    public Transaction deposit(String reference) {
        PaystackVerificationResponse response = paymentService.verifyPaymentStatus(reference);

        String userEmail = response.getData().getCustomer().getEmail();
        User user = userOutputPort.getUser(userEmail);
        BigDecimal amount = new BigDecimal(response.getData().getAmount());
        Wallet wallet = user.getWallet().deposit(amount);
        wallet = walletOutputPort.saveWallet(wallet);

        Transaction transaction = createTransaction(response);
        transaction.setWallet(wallet);
        transaction.setType(TransactionType.DEPOSIT);
        transaction = transactionOutputPort.save(transaction);
        if (wallet.getTransactions() == null) {
            wallet.setTransactions(new HashSet<>());
        }

        wallet.getTransactions().add(transaction);
        walletOutputPort.saveWallet(wallet);

        return transaction;
    }


    private Transaction createTransaction(PaystackVerificationResponse verificationResponse) {
        TransactionStatus transactionStatus = switch (verificationResponse.getData().getStatus()) {
            case SUCCESS -> TransactionStatus.SUCCESS;
            case FAILED -> TransactionStatus.FAILED;
            case PENDING -> TransactionStatus.PENDING;
            case REVERSED -> TransactionStatus.REVERSED;
            case QUEUED -> TransactionStatus.QUEUED;
            case ABANDONED -> TransactionStatus.ABANDONED;
            case ONGOING -> TransactionStatus.ONGOING;
            case PROCESSING -> TransactionStatus.PROCESSING;
        };
        BigDecimal amount = new BigDecimal(verificationResponse.getData().getAmount());
        return new Transaction().buildTransaction(amount, verificationResponse.getData().getReference(),
                TransactionType.DEPOSIT, transactionStatus,
                getWallet(verificationResponse.getData().getCustomer().getEmail()));


    }

    private Transaction buildTransaction(PaystackVerificationResponse verificationResponse, TransactionStatus transactionStatus) {
        return Transaction.builder()
                .amount(new BigDecimal(verificationResponse.getData().getAmount()))
                .type(TransactionType.DEPOSIT)
                .status(transactionStatus)
                .wallet(getWallet(verificationResponse.getData().getCustomer().getEmail()))
                .reference(verificationResponse.getData().getReference())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    private Boolean verifyUser(String email, String password) {
        User user = userOutputPort.getUser(email);
        if (user == null) throw new UserNotFoundException("User not found");
        return passwordEncoder.matches(password, user.getPassword());
    }

    private User getUserByEmail(String email) {
        User user = userOutputPort.getUser(email);
        if (user == null) throw new UserNotFoundException("User not found");
        return user;
    }

    private void updateWalletBalance(String email, Transaction transaction) {
        Wallet wallet = getUserByEmail(email).getWallet();
        wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        walletOutputPort.saveWallet(wallet);
    }


    @Override
    public BigDecimal getBalance(String emailAddress) {
        User user = getUserByEmail(emailAddress);
        return user.getWallet().getBalance();
    }

    @Override
    public Wallet getWallet(Long walletId) {
        Wallet wallet = walletOutputPort.getWallet(walletId);
        if (wallet == null) throw new WalletNotFoundException("Wallet not found");
        return wallet;
    }

    @Override
    public Wallet getWallet(String email) {
        User user = getUserByEmail(email);
        return user.getWallet();
    }
}
