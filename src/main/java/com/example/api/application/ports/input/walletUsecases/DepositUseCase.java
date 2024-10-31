package com.example.api.application.ports.input.walletUsecases;

import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackPaymentResponse;
import com.example.api.domain.models.Transaction;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.DepositRequest;

public interface DepositUseCase {
    PaystackPaymentResponse initializeDeposit(DepositRequest request);
    Transaction deposit(String reference);
}
