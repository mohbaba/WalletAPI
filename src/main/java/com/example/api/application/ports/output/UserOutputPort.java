package com.example.api.application.ports.output;

import com.example.api.domain.models.User;

import java.util.List;
import java.util.Optional;

public interface UserOutputPort {
    User saveUser(User user);

    User getUser(String email);
    Optional<User> getUser(Long userId);
    List<User> getAllUsers();
}
