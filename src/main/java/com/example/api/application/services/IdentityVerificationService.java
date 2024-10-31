package com.example.api.application.services;

import com.example.api.application.dto.requests.VerifyPhoneNumberRequest;
import com.example.api.application.ports.input.identityVerificationUseCases.VerifyPhoneNumberUseCase;
import com.example.api.domain.exceptions.VerificationFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.example.api.utils.URLS.VERIFY_PHONE_NUMBER_URL;

@Service
@Slf4j
public class IdentityVerificationService implements VerifyPhoneNumberUseCase {
    @Value("${api.prembly.key}")
    private String apiKey;

    @Value("${api.prembly.id}")
    private String appId;
    @Override
    public HttpResponse<String> verifyPhoneNumber(VerifyPhoneNumberRequest request) {
        System.out.println(apiKey);
        log.info("Verifying phone number: {}", request.getNumber());
        String requestBody = null;
        try {
             requestBody = new ObjectMapper().writeValueAsString(request);
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(VERIFY_PHONE_NUMBER_URL))
                 .header("Accept", "application/json")
                .header("x-api-key", apiKey)
                .header("app_id", appId)
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (InterruptedException | IOException e) {
            throw new VerificationFailedException("Identity verification failed "+e);
        }


    }
}
