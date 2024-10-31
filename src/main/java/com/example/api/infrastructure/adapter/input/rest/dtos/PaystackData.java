package com.example.api.infrastructure.adapter.input.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaystackData {
    private String authorizationUrl;
    private String accessCode;
    private String reference;
}
