package com.example.api.application.ports.input.paystackUsecases;

import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackPaymentResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AcceptPaymentRequest;

public interface InitializePayment {
    PaystackPaymentResponse initializePayment(AcceptPaymentRequest request);
}
