package com.example.api.domain.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void deposit() {
        Wallet wallet = new Wallet();
        wallet.deposit(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), wallet.getBalance());
    }

    @Test
    void withdraw() {
        Wallet wallet = new Wallet();
        assertThrows(ForbiddenTransactionException.class, () -> wallet.withdraw(new BigDecimal(2000)));
    }

    @Test
    void withdrawNegativeAmount() {
        Wallet wallet = new Wallet();
        assertThrows(ForbiddenTransactionException.class, () -> wallet.withdraw(new BigDecimal(-5000)));
    }


}