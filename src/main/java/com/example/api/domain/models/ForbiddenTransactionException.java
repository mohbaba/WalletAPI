package com.example.api.domain.models;

public class ForbiddenTransactionException extends RuntimeException {
    public ForbiddenTransactionException(String message) {
        super(message);
    }
}
