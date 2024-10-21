package com.example.api.application.ports.input.userUsecases;

import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.RegisterUserRequest;

public interface RegisterUserUseCase {
    User registerUser(User user);
}
