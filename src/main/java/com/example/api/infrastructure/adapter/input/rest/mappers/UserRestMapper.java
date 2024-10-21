package com.example.api.infrastructure.adapter.input.rest.mappers;


import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.RegisterUserRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.RegisterUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    User toUser(RegisterUserRequest registerUserRequest);
    RegisterUserResponse toRegisterUserResponse(User user);
}
