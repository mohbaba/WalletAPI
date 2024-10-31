package com.example.api.application.ports.input.userUsecases;

import com.example.api.infrastructure.adapter.input.rest.dtos.requests.LoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;

public interface LoginUserUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
