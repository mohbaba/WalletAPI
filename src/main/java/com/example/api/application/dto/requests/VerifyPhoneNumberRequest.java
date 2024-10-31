package com.example.api.application.dto.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyPhoneNumberRequest {
    private String number;
}
