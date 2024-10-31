package com.example.api.application.services;

import com.example.api.application.ports.input.transactionUsecases.GetAllTransactionsUseCase;
import com.example.api.application.ports.input.transactionUsecases.GetTransactionUseCase;
import com.example.api.application.ports.output.TransactionOutputPort;
import com.example.api.application.ports.output.UserOutputPort;
import com.example.api.domain.exceptions.TransactionNotFoundException;
import com.example.api.domain.exceptions.UserNotFoundException;
import com.example.api.domain.models.Transaction;
import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.TransactionResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionService implements GetTransactionUseCase, GetAllTransactionsUseCase {

    private TransactionOutputPort transactionOutputPort;
    private UserOutputPort userOutputPort;
    @Override
    public Transaction getTransaction(Long id) {
        Transaction transaction = transactionOutputPort.getTransaction(id);
        if (transaction == null) throw new TransactionNotFoundException("Transaction not found");
        return transactionOutputPort.getTransaction(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionOutputPort.getAllTransactions();
    }

    @Override
    public List<Transaction> getAllTransactionsFor(String email) {
        User user = userOutputPort.getUser(email);
        if (user == null) throw new UserNotFoundException("User not found");
        List<Transaction> transactions =transactionOutputPort.getAllTransactionsFor(user.getWallet().getId());
        if (transactions == null) throw new TransactionNotFoundException("No transactions found for this user");
        return transactions;
    }

}
