package com.example.api.infrastructure.adapter.input.rest;

import com.example.api.application.ports.input.walletUsecases.CreateWalletUseCase;
import com.example.api.application.services.WalletService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/wallets")
public class WalletRestAdapter {
    private CreateWalletUseCase createWalletUseCase;

}
