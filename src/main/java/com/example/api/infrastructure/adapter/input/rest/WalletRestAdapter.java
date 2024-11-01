package com.example.api.infrastructure.adapter.input.rest;

import com.example.api.application.ports.input.walletUsecases.DepositUseCase;
import com.example.api.application.ports.input.walletUsecases.GetBalanceUseCase;
import com.example.api.application.ports.input.walletUsecases.GetWalletUseCase;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackPaymentResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.DepositRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.GetWalletResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.TransactionResponse;
import com.example.api.infrastructure.adapter.input.rest.mappers.TransactionRestMapper;
import com.example.api.infrastructure.adapter.input.rest.mappers.WalletRestMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallets")
@RequiredArgsConstructor
public class WalletRestAdapter {
    private final WalletRestMapper walletRestMapper;
    private final TransactionRestMapper transactionRestMapper;
//    private final CreateWalletUseCase createWalletUseCase;
    private final DepositUseCase depositUseCase;
    private final GetBalanceUseCase getBalanceUseCase;
    private final GetWalletUseCase getWalletUseCase;

    @GetMapping("/wallet/{owner}")
    public ResponseEntity<GetWalletResponse> getWallet(@PathVariable String owner){
        GetWalletResponse response = walletRestMapper.toWalletResponse(this.getWalletUseCase.getWallet(owner));
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @GetMapping("/balance/{owner}")
    public ResponseEntity<?> getBalance(@PathVariable String owner){
        return new ResponseEntity<>(this.getBalanceUseCase.getBalance(owner), HttpStatus.OK);
    }

    @PostMapping("/initializeDeposit")
    public ResponseEntity<PaystackPaymentResponse> initializeDeposit(@RequestBody DepositRequest depositRequest){
        PaystackPaymentResponse paymentResponse = this.depositUseCase.initializeDeposit(depositRequest);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    @PostMapping("/verifyPayment/{reference}")
    public ResponseEntity<TransactionResponse> verifyPayment(@PathVariable String reference){
        TransactionResponse response = transactionRestMapper.toTransactionResponse(this.depositUseCase.deposit(reference));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
