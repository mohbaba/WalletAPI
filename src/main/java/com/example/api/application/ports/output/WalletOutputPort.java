package com.example.api.application.ports.output;

import com.example.api.domain.models.Wallet;

public interface WalletOutputPort {
    Wallet saveWallet(Wallet walletToSave);

}
