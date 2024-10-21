package com.example.api.infrastructure.adapter.input.rest;


import com.example.api.application.ports.input.userUsecases.DeleteUserUseCase;
import com.example.api.application.ports.input.userUsecases.FindUserUseCase;
import com.example.api.application.ports.input.userUsecases.ListUserUseCase;
import com.example.api.application.ports.input.userUsecases.RegisterUserUseCase;
import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.RegisterUserRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.RegisterUserResponse;
import com.example.api.infrastructure.adapter.input.rest.mappers.UserRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final RegisterUserUseCase registerUserUseCase;
    private final UserRestMapper userRestMapper;
//    private final FindUserUseCase findUserUseCase;
//    private final DeleteUserUseCase deleteUserUseCase;
//    private final ListUserUseCase listUserUseCase;


    @PostMapping
    ResponseEntity<RegisterUserResponse> createUser(@RequestBody RegisterUserRequest registerUserRequest){
        User user = userRestMapper.toUser(registerUserRequest);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user = registerUserUseCase.registerUser(user);
        return new ResponseEntity<>(this.userRestMapper.toRegisterUserResponse(user), HttpStatus.CREATED);
    }
}
