package com.example.api.infrastructure.adapter.input.rest.mappers;


import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.RegisterUserRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.UpdateUserRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserRestMapper {
    User toUser(RegisterUserRequest registerUserRequest);
    User toUser(UpdateUserRequest updateUserRequest);
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponse(List<User> users);
}
