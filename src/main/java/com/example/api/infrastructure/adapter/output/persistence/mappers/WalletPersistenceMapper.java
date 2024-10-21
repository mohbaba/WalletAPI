package com.example.api.infrastructure.adapter.output.persistence.mappers;

import com.example.api.domain.models.Wallet;
import com.example.api.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WalletPersistenceMapper {
    Wallet toWallet(WalletEntity entity);
    WalletEntity toWalletEntity(Wallet wallet);
}
