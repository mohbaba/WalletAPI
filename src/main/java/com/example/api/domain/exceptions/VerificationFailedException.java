package com.example.api.domain.exceptions;

public class VerificationFailedException extends RuntimeException{
    public VerificationFailedException(String message){
        super(message);
    }
}
