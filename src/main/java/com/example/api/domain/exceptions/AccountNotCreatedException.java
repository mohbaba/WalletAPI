package com.example.api.domain.exceptions;

public class AccountNotCreatedException extends RuntimeException{
    public AccountNotCreatedException(String message) {
        super(message);
    }
}
