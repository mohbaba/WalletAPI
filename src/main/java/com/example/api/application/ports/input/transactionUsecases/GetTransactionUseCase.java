package com.example.api.application.ports.input.transactionUsecases;

import com.example.api.domain.models.Transaction;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.TransactionResponse;

public interface GetTransactionUseCase {
    Transaction getTransaction(Long id);
}
