package com.example.api.application.ports.input.paystackUsecases;

import com.example.api.infrastructure.adapter.input.rest.dtos.requests.TransferRefRequest;

public interface GenerateTransferRefUseCase {
    TransferRefRequest generateTransferRef(TransferRefRequest request);
}
