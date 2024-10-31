package com.example.api.application.ports.output;

import com.example.api.domain.models.Transaction;

import java.util.List;

public interface TransactionOutputPort {
    Transaction save(Transaction transaction);
    Transaction getTransaction(Long id);

    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsFor(Long walletId);
}
