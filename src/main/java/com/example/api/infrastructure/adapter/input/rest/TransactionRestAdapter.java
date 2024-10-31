package com.example.api.infrastructure.adapter.input.rest;


import com.example.api.application.ports.input.transactionUsecases.GetAllTransactionsUseCase;
import com.example.api.application.ports.input.transactionUsecases.GetTransactionUseCase;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.TransactionResponse;
import com.example.api.infrastructure.adapter.input.rest.mappers.TransactionRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionRestAdapter {
    private final TransactionRestMapper transactionRestMapper;
    private final GetTransactionUseCase getTransactionUseCase;
    private final GetAllTransactionsUseCase getAllTransactionsUseCase;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long transactionId) {
        return new ResponseEntity<>(transactionRestMapper.toTransactionResponse(getTransactionUseCase.getTransaction(transactionId)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(){
        return new ResponseEntity<>(transactionRestMapper.toTransactionResponseList(getAllTransactionsUseCase.getAllTransactions()), HttpStatus.OK);
    }

    @GetMapping("user/{email}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByEmail(@PathVariable String email){
        return new ResponseEntity<>(transactionRestMapper.toTransactionResponseList(getAllTransactionsUseCase.getAllTransactionsFor(email)), HttpStatus.OK);
    }

//    @PostMapping("/user/date")
//    public ResponseEntity<List<TransactionResponse>> getTransactionsByDate(@RequestBody String date){
//        return new ResponseEntity<>(transactionRestMapper.toTransactionResponseList(getAllTransactionsUseCase.getAllTransactionsForDate(date)), HttpStatus.OK);
//    }

//    @PostMapping("/")
}
