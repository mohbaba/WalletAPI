package com.example.api.infrastructure.adapter.input.rest.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RecipientType {
    BANK("nuban"),
    MOBILE_MONEY("mobile_money");

    private final String value;

    RecipientType(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static RecipientType fromValue(String value) {
        for (RecipientType type : RecipientType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid recipient type: " + value);
    }
}
