package com.example.api.application.ports.input.walletUsecases;

import com.example.api.domain.models.Wallet;

public interface CreateWalletUseCase {
    Wallet createWalletFor(String email);
}
