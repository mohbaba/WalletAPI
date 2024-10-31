package com.example.api.application.ports.input.walletUsecases;

import com.example.api.application.services.payment.PaystackPaymentResponse;
import com.example.api.application.services.payment.PaystackVerificationResponse;
import com.example.api.domain.models.Transaction;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.DepositRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.VerifyPaymentRequest;

public interface DepositUseCase {
    PaystackPaymentResponse initializeDeposit(DepositRequest request);
    Transaction deposit(String reference);
}
