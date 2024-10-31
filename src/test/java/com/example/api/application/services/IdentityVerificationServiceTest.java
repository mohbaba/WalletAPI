package com.example.api.application.services;

import com.example.api.application.dto.requests.VerifyPhoneNumberRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IdentityVerificationServiceTest {
    @Autowired
    private IdentityVerificationService identityVerificationService;


    @Test
    void verifyPhoneNumber() {
        VerifyPhoneNumberRequest request = new VerifyPhoneNumberRequest();
        request.setNumber("08155531802");
        var response = identityVerificationService.verifyPhoneNumber(request);
        System.out.println(response);
        assertEquals(response.statusCode(), 200);
    }
}