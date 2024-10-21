package com.example.api.application.services;

import com.example.api.application.ports.input.walletUsecases.CreateWalletUseCase;
import com.example.api.application.ports.output.WalletOutputPort;
import com.example.api.domain.models.Wallet;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WalletService implements CreateWalletUseCase {
    private final WalletOutputPort walletOutputPort;
    @Override
    public Wallet createWallet(Wallet wallet) {
        return null;
    }
}
