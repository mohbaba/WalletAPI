package com.example.api.application.ports.input.userUsecases;

import com.example.api.domain.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RegisterUserUseCase {
    User register(User user) throws JsonProcessingException;
}
