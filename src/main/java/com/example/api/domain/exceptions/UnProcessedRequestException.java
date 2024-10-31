package com.example.api.domain.exceptions;

public class UnProcessedRequestException extends RuntimeException {
    public UnProcessedRequestException(String message) {
        super(message);
    }
}
