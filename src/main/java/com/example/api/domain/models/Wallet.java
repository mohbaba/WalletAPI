package com.example.api.domain.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Wallet {
    private Long id;
    private BigDecimal balance = new BigDecimal(0);
    private Set<Transaction> transactions = new HashSet<>();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeCreated;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeUpdated;


    public Wallet deposit(BigDecimal amount) {
        validateDepositAmount(amount);
        balance = balance.add(amount);
        return this;
    }

    public Wallet withdraw(BigDecimal amount) {
        validateWithdrawalAmount(amount);
        balance = balance.subtract(amount);
        return this;
    }

    public void validateDepositAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ForbiddenTransactionException("Transaction could not be completed");
        }
    }

    public void validateWithdrawalAmount(BigDecimal amount) {
        if (amount.compareTo(balance) > 0) throw new ForbiddenTransactionException("Transaction could not be completed");
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new ForbiddenTransactionException("Transaction could not be completed");
    }

}
