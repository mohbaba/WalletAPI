package com.example.api.application.services;

import com.example.api.application.ports.input.userUsecases.RegisterUserUseCase;
import com.example.api.application.ports.output.UserOutputPort;
import com.example.api.domain.exceptions.UserExistsException;
import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.RegisterUserRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class UserService implements RegisterUserUseCase {
    private UserOutputPort userOutputPort;
    @Override
    public User registerUser(User user) {
        checkUserExist(user.getEmail());

        return userOutputPort.saveUser(user);
    }

    private void checkUserExist(String email) {
        User user = userOutputPort.getUser(email);
        if (user!= null) {
            throw new UserExistsException("User with the email already exist");
        }
    }
}
