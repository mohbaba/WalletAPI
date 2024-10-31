package com.example.api.infrastructure.adapter.input.rest;


import com.example.api.application.ports.input.userUsecases.*;
import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.LoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.RegisterUserRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.UpdateUserRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.UserResponse;
import com.example.api.infrastructure.adapter.input.rest.mappers.UserRestMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestAdapter {
    private final RegisterUserUseCase registerUserUseCase;
    private final UserRestMapper userRestMapper;
    private final UpdateUserUseCase updateUserUseCase;
    private final FindUserUseCase findUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final ListUsersUseCase listUsersUseCase;

//    @PreAuthorize("hasRole('USER')")
    @PostMapping("/public/register")
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) throws JsonProcessingException {
        User user = userRestMapper.toUser(registerUserRequest);
        user = registerUserUseCase.register(user);
        return new ResponseEntity<>(this.userRestMapper.toUserResponse(user), HttpStatus.CREATED);
    }


    @PostMapping("/public/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(this.loginUserUseCase.login(loginRequest), HttpStatus.OK);
    }


    @PatchMapping("/update")
    ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest){
        User user = userRestMapper.toUser(updateUserRequest);
        user = updateUserUseCase.updateUser(user);
        return new ResponseEntity<>(this.userRestMapper.toUserResponse(user), HttpStatus.OK);
    }


    @GetMapping("/{email}")
    ResponseEntity<UserResponse> getUser(@PathVariable String email){
        User user = findUserUseCase.getUser(email);
        return new ResponseEntity<>(this.userRestMapper.toUserResponse(user), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        User user = findUserUseCase.getUser(id);
        return new ResponseEntity<>(userRestMapper.toUserResponse(user), HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserResponse>> listUsers(){
        List<User> users = listUsersUseCase.getAllUsers();
        return new ResponseEntity<>(this.userRestMapper.toUserResponse(users), HttpStatus.OK);
    }

}
