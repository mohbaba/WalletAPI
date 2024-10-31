package com.example.api.application.services.payment;

import com.example.api.domain.models.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Data {
    private TransactionStatus status;
    private String reference;
    private Integer amount;
    private String gateway_response;
    private String paid_at;
    private Authorization authorization;
    private Customer customer;



}
