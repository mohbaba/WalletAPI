package com.example.api.infrastructure.adapter.input.rest.dtos.responses;

import com.example.api.domain.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetWalletResponse {
    private Long id;
    private BigDecimal balance;
    private Set<Long> transactionIds;

}
