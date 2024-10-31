package com.example.api.application.ports.input.walletUsecases;

import com.example.api.domain.models.Transaction;

public interface TransferUseCase {
    Transaction transfer();
}
