package com.example.api.infrastructure.adapter.input.rest.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferRefRequest {

    private String source;
    private String reason;
    private Double amount;
    private String reference;
    private String recipient;

}

