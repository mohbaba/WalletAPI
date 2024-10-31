package com.example.api.infrastructure.adapter.input.rest.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcceptPaymentRequest {
    private String email;
    private String amount;
}
