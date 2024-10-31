package com.example.api.infrastructure.adapter.input.rest.dtos.requests;

import com.example.api.domain.models.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {
    @NotEmpty(message = "firstName must not be empty")
    @NotNull(message = "firstName must not be null")
    @NotBlank(message = "firstName must not be blank")
    private String firstName;
    @NotEmpty(message = "lastName must not be empty")
    @NotNull(message = "lastName must not be null")
    @NotBlank(message = "lastName must not be blank")
    private String lastName;
    @NotEmpty(message = "email must not be empty")
    @NotNull(message = "email must not be null")
    @NotBlank(message = "email must not be blank")
    private String email;
    @NotEmpty(message = "PhoneNumber must not be empty")
    @NotNull(message = "PhoneNumber must not be null")
    @NotBlank(message = "PhoneNumber must not be blank")
    private String phoneNumber;
    @NotEmpty(message = "Password must not be empty")
    @NotNull(message = "Password must not be null")
    @NotBlank(message = "Password must not be blank")
    private String password;
    private Role role;
}
