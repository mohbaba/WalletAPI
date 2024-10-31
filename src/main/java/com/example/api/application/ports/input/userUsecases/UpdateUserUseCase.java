package com.example.api.application.ports.input.userUsecases;

import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.UpdateUserRequest;

public interface UpdateUserUseCase {
    User updateUser(User updateUser);
}
