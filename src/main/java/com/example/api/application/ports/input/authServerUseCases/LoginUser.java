package com.example.api.application.ports.input.authServerUseCases;

import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AuthLoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;

public interface LoginUser {
    LoginResponse loginUser(AuthLoginRequest loginRequest);
}
