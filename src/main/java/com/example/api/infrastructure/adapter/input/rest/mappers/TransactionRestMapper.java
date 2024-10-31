package com.example.api.infrastructure.adapter.input.rest.mappers;

import com.example.api.domain.models.Transaction;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TransactionRestMapper {
    @Mapping(source = "wallet.id", target = "walletId")
    TransactionResponse toTransactionResponse(Transaction transaction);

    List<TransactionResponse> toTransactionResponseList(List<Transaction> transaction);
}
