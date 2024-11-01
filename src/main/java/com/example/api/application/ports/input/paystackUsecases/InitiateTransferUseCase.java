package com.example.api.application.ports.input.paystackUsecases;

import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.InitiateTransferResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.TransferRefRequest;

public interface InitiateTransferUseCase {
    InitiateTransferResponse initiateTransfer(TransferRefRequest transferRefRequest);
}
