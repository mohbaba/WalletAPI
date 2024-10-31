package com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class InitiateTransferResponse {

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private TransferData data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransferData {

        @JsonProperty("domain")
        private String domain;

        @JsonProperty("amount")
        private int amount;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("reference")
        private String reference;

        @JsonProperty("source")
        private String source;

        @JsonProperty("reason")
        private String reason;

        @JsonProperty("status")
        private String status;

        @JsonProperty("transfer_code")
        private String transferCode;

        @JsonProperty("id")
        private int id;

        @JsonProperty("integration")
        private int integration;

        @JsonProperty("recipient")
        private int recipient;

        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("updatedAt")
        private String updatedAt;
    }
}

