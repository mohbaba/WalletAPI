package com.example.api.domain.exceptions;

public class ConnectionErrorException extends RuntimeException {
    public ConnectionErrorException(String message) {
        super(message);
    }
}
