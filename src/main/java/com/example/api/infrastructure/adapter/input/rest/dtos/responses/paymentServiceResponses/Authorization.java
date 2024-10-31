package com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Authorization {
    private String authorization_code;
    private String card_type;
    private String bank;
    private String last4;
}

