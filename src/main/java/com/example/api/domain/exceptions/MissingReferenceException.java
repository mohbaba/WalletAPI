package com.example.api.domain.exceptions;

public class MissingReferenceException extends RuntimeException{
    public MissingReferenceException(String message) {
        super(message);
    }
}
