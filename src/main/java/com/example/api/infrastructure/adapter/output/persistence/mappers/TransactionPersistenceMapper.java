package com.example.api.infrastructure.adapter.output.persistence.mappers;

import com.example.api.domain.models.Transaction;
import com.example.api.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TransactionPersistenceMapper {
    TransactionEntity toTransactionEntity(Transaction transaction);
    @Mapping(target = "wallet.transactions", ignore = true)
    Transaction toTransaction(TransactionEntity entity);
    List<Transaction> toTransactionList(List<TransactionEntity> transactionEntity);
}
