package com.example.api.infrastructure.adapter.input.rest.dtos.responses;

import com.example.api.domain.models.Wallet;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Wallet wallet;
}
