package com.example.api.application.services.payment;

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
