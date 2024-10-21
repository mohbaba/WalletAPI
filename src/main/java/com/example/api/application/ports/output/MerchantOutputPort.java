package com.example.api.application.ports.output;

import com.example.api.domain.models.Merchant;

public interface MerchantOutputPort {
    Merchant save(Merchant merchant);
}
