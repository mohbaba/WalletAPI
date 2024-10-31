package com.example.api.application.ports.input.userUsecases;

import com.example.api.domain.models.User;

import java.util.List;

public interface ListUsersUseCase {
    List<User> getAllUsers();
}
