package com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class PaystackVerificationResponse {
    private String status;
    private String message;
    private Data data;


}
