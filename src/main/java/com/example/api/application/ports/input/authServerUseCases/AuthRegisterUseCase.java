package com.example.api.application.ports.input.authServerUseCases;

import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AuthRegisterRequest;

public interface AuthRegisterUseCase {
    String authRegisterUser(AuthRegisterRequest request);
}
