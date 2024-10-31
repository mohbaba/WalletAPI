package com.example.api.infrastructure.adapter.input.rest.dtos.responses;

import com.example.api.domain.models.TransactionStatus;
import com.example.api.domain.models.TransactionType;
import com.example.api.domain.models.Wallet;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String reference;
    private TransactionType type;
    private TransactionStatus status;
    private Long walletId;
}
