package com.example.api.infrastructure.adapter.output.persistence.mappers;

import com.example.api.domain.models.Transaction;
import com.example.api.domain.models.Wallet;
import com.example.api.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import com.example.api.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface WalletPersistenceMapper {
    @Mapping(target = "transactions", qualifiedByName = "flattenTransactionList")
    Wallet toWallet(WalletEntity entity);

    WalletEntity toWalletEntity(Wallet wallet);

    @Named("flattenTransactionList")
    default Set<Transaction> flattenTransactionList(Set<TransactionEntity> transactions) {
        if (transactions == null) return null;

        return transactions.stream()
                .map(this::flattenTransaction)
                .collect(Collectors.toSet());
    }

    private Transaction flattenTransaction(TransactionEntity transaction) {
        return Transaction.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .reference(transaction.getReference())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .build();
    }
}
