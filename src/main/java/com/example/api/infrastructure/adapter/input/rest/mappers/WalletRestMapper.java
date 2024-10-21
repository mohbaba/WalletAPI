package com.example.api.infrastructure.adapter.input.rest.mappers;

import com.example.api.domain.models.Wallet;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.CreateWalletRequest;
import org.mapstruct.Mapper;

@Mapper
public interface WalletRestMapper {
    Wallet toWallet(CreateWalletRequest request);
}
