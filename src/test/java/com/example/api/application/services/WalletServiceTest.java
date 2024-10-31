package com.example.api.application.services;

import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackPaymentResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackVerificationResponse;
import com.example.api.domain.models.*;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.DepositRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class WalletServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private PaymentService paymentService;


    @Test
    @DisplayName("test that user can deposit to wallet")
    void testInitializeDepositToWallet() {
        PaystackPaymentResponse paymentResponse = initializeDeposit1();
        assertTrue(paymentResponse.isStatus());
        assertNotNull(paymentResponse.getData());
        assertNotNull(paymentResponse.getData().getAuthorization_url());
        assertNotNull(paymentResponse.getData().getAccess_code());
        assertNotNull(paymentResponse.getData().getReference());

    }

    @Test
    @DisplayName("test that wallet can be created")
    void testCreateWallet() {
        Wallet wallet = walletService.createWalletFor("moh.baba@example.com");
        User user = userService.getUser("moh.baba@example.com");
        assertNotNull(wallet);
        assertEquals(new BigDecimal("0.00"), user.getWallet().getBalance());

    }

    @Test
    @DisplayName("test that user can deposit to wallet")
    void testVerifyDepositToWallet() throws InterruptedException {

        PaystackPaymentResponse paymentResponse = initializeDeposit2();
        System.out.println(paymentResponse.getData().getAuthorization_url());
        Thread.sleep(15_000);

        PaystackVerificationResponse paystackVerificationResponse = paymentService.verifyPaymentStatus(paymentResponse.getData().getReference());
        Transaction transaction = walletService.deposit(paymentResponse.getData().getReference());

        assertTrue(paymentResponse.isStatus());
        assertNotNull(transaction.getId());
        assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        assertEquals(transaction.getAmount().intValue(), paystackVerificationResponse.getData().getAmount());
        assertEquals(transaction.getType(), TransactionType.DEPOSIT);
    }
    @Test
    void testCheckWalletBalance(){
        BigDecimal balance = walletService.getBalance("john.doe@example.com");
        assertEquals(new BigDecimal(1000), balance);
    }

    private PaystackPaymentResponse initializeDeposit1() {
        DepositRequest request = new DepositRequest();
        request.setEmailAddress("john.doe@example.com");
        request.setPassword("hashedpassword1");
        request.setAmount(new BigDecimal(10000));
        PaystackPaymentResponse paymentResponse = walletService.initializeDeposit(request);
        return paymentResponse;
    }
    private PaystackPaymentResponse initializeDeposit2() {
        DepositRequest request = new DepositRequest();
        request.setEmailAddress("moh.baba@example.com");
        request.setPassword("hashedpassword1");
        request.setAmount(new BigDecimal(10000));
        testCreateWallet();
        PaystackPaymentResponse paymentResponse = walletService.initializeDeposit(request);
        return paymentResponse;
    }

    @Test
    void createWalletFor() {
    }

    @Test
    void initializeDeposit() {
    }

    @Test
    void deposit() {
    }

    @Test
    void getBalance() {
    }

    @Test
    void getWallet() {
    }

    @Test
    void testGetWallet() {
    }
}