package com.example.api.infrastructure.adapter.input.rest.dtos.requests;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositRequest {
    private BigDecimal amount;
    private String description;
    private String emailAddress;
    private String password;
}
