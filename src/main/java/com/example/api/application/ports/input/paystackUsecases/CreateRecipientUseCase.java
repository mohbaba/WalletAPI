package com.example.api.application.ports.input.paystackUsecases;

import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.TransferRecipientResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.CreateRecipientRequest;

public interface CreateRecipientUseCase {
    TransferRecipientResponse createRecipient(CreateRecipientRequest createRecipientRequest);
}
