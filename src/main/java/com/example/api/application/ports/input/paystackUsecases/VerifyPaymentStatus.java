package com.example.api.application.ports.input.paystackUsecases;

import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackVerificationResponse;

public interface VerifyPaymentStatus {
    public PaystackVerificationResponse verifyPaymentStatus(String reference);
}
