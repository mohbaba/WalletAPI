package com.example.api.application.services;

import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.AuthUser;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AuthLoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.LoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    void createUser() {
        AuthUser authUser = new AuthUser();
        authUser.setEmail("becexi6393@mabano.com");
        authUser.setPassword("password");
        authUser.setFirstName("John");
        authUser.setLastName("Doe");
        authUser.setUsername("becexi6393@mabano.com");

        authService.createUser(authUser);
    }
    @Test
    void testLoginUser() {
        AuthLoginRequest loginRequest = new AuthLoginRequest();
        loginRequest.setUsername("becexi6393@mabano.com");
        loginRequest.setPassword("password");

        try {
            LoginResponse response = authService.loginUser(loginRequest);
            System.out.println(new ObjectMapper().writeValueAsString(response));
            assertNotNull(response);
            assertNotNull(response.getAccessToken());
            assertTrue(response.getExpiresIn() > 0);
            assertTrue(0 < response.getRefreshExpiresIn(), "Check that the RefreshToken expiry time is greater than Zero ");
        } catch (Exception e) {
            fail("Login should not throw an exception for valid credentials."+e);
        }
    }


}