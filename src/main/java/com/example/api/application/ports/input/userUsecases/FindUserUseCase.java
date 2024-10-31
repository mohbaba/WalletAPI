package com.example.api.application.ports.input.userUsecases;

import com.example.api.domain.models.User;

public interface FindUserUseCase {
    User getUser(String email);
    User getUser(Long userId);
}
