package com.example.api.application.ports.input.paystackUsecases;

import com.example.api.application.services.payment.TransferRecipientResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.CreateRecipientRequest;

public interface CreateRecipientUseCase {
    TransferRecipientResponse createRecipient(CreateRecipientRequest createRecipientRequest);
}
