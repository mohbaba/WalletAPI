package com.example.api.application.ports.input.walletUsecases;

import java.math.BigDecimal;

public interface GetBalanceUseCase {
    BigDecimal getBalance(String emailAddress);
}
