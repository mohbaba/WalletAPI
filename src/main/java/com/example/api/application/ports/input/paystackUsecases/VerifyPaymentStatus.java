package com.example.api.application.ports.input.paystackUsecases;

import com.example.api.application.services.payment.PaystackVerificationResponse;

public interface VerifyPaymentStatus {
    public PaystackVerificationResponse verifyPaymentStatus(String reference);
}
