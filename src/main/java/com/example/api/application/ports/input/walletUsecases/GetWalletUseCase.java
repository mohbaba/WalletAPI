package com.example.api.application.ports.input.walletUsecases;

import com.example.api.domain.models.Wallet;

public interface GetWalletUseCase {
    Wallet getWallet(Long walletId);
    Wallet getWallet(String email);
}
