package com.example.api.application.ports.input.identityVerificationUseCases;

import com.example.api.application.dto.requests.VerifyPhoneNumberRequest;

public interface VerifyPhoneNumberUseCase {
    Object verifyPhoneNumber(VerifyPhoneNumberRequest request);
}
