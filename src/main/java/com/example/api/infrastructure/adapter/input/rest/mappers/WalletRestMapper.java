package com.example.api.infrastructure.adapter.input.rest.mappers;

import com.example.api.domain.models.Transaction;
import com.example.api.domain.models.Wallet;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.CreateWalletRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.GetWalletResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface WalletRestMapper {
    Wallet toWallet(CreateWalletRequest request);
    @Mapping(target = "transactionIds", source = "wallet", qualifiedByName = "mapTransactionIds")
    GetWalletResponse toWalletResponse(Wallet wallet);

    @Named("mapTransactionIds")
     default Set<Long> mapTransactionIds(Wallet wallet) {
        if (wallet.getTransactions() == null) return new HashSet<>();
        return wallet.getTransactions().stream().map(Transaction::getId).collect(Collectors.toSet());
    }
}
