package com.example.api.infrastructure.adapter;

import com.example.api.application.ports.output.WalletOutputPort;
import com.example.api.domain.models.Wallet;
import com.example.api.infrastructure.adapter.output.persistence.entities.WalletEntity;
import com.example.api.infrastructure.adapter.output.persistence.mappers.WalletPersistenceMapper;
import com.example.api.infrastructure.adapter.output.persistence.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletOutputPort {

    private final WalletRepository walletRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;

    @Override
    public Wallet saveWallet(Wallet walletToSave) {
        WalletEntity entity = walletPersistenceMapper.toWalletEntity(walletToSave);
        entity = walletRepository.save(entity);
        return walletPersistenceMapper.toWallet(entity);
    }

    @Override
    public Wallet getWallet(Long walletId) {
        return walletPersistenceMapper.toWallet(walletRepository.findById(walletId).orElse(null));
    }

}
