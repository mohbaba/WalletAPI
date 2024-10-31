package com.example.api.infrastructure.adapter;

import com.example.api.application.ports.output.TransactionOutputPort;
import com.example.api.domain.models.Transaction;
import com.example.api.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import com.example.api.infrastructure.adapter.output.persistence.mappers.TransactionPersistenceMapper;
import com.example.api.infrastructure.adapter.output.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionPersistenceAdapter implements TransactionOutputPort {
    private final TransactionPersistenceMapper transactionPersistenceMapper;
    private final TransactionRepository transactionRepository;
    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = transactionPersistenceMapper.toTransactionEntity(transaction);
        return transactionPersistenceMapper.toTransaction(transactionRepository.save(transactionEntity));
    }

    @Override
    public Transaction getTransaction(Long id) {
        TransactionEntity transactionEntity = transactionRepository.findById(id).orElse(null);
        return transactionPersistenceMapper.toTransaction(transactionEntity);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<TransactionEntity> transactionEntities = transactionRepository.findAll();
        return transactionPersistenceMapper.toTransactionList(transactionEntities);
    }

    @Override
    public List<Transaction> getAllTransactionsFor(Long walletId) {
        List<TransactionEntity> transactionEntities = transactionRepository.findAllByWalletId(walletId);
        return transactionPersistenceMapper.toTransactionList(transactionEntities);
    }


}
