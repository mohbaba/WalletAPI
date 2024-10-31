package com.example.api.application.services;

import com.example.api.application.ports.input.paystackUsecases.*;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.InitiateTransferResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackPaymentResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.PaystackVerificationResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.TransferRecipientResponse;
import com.example.api.domain.exceptions.ConnectionErrorException;
import com.example.api.domain.exceptions.MissingReferenceException;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AcceptPaymentRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.CreateRecipientRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.TransferRefRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static com.example.api.utils.URLS.*;


@RequiredArgsConstructor
public class PaymentService implements InitializePayment, VerifyPaymentStatus, CreateRecipientUseCase, GenerateTransferRefUseCase, InitiateTransferUseCase {
    @Value("${cloud.paystack.key}")
    private String API_KEY;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Assuming ObjectMapper is a dependency for JSON serialization/deserialization

    @Override
    public PaystackPaymentResponse initializePayment(AcceptPaymentRequest request){
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(request);
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(ACCEPT_PAYMENT_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                PaystackPaymentResponse paystackResponse = objectMapper.readValue(response.body(), PaystackPaymentResponse.class);
                System.out.println("Payment initialized: " + paystackResponse.getData().getAuthorization_url());
                return paystackResponse;
            }else {
                throw new RuntimeException("Payment initialization failed"+ response);
            }
        } catch (IOException | InterruptedException e) {
            throw new ConnectionErrorException("An error occurred while initializing, try again");
        }

    }

    @Override
    public PaystackVerificationResponse verifyPaymentStatus(String reference) {
        String url = VERIFY_PAYMENT_URL + reference;
        try {
            HttpClient httpClient = HttpClient.newBuilder().build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + API_KEY)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), PaystackVerificationResponse.class);

        } catch (IOException | InterruptedException e) {
            throw new ConnectionErrorException("Error while verifying transaction status"+ e);
        }
    }

    @Override
    public TransferRecipientResponse createRecipient(CreateRecipientRequest createRecipientRequest) {
        String requestBody = null;
        try{
            requestBody = objectMapper.writeValueAsString(createRecipientRequest);
            HttpClient httpClient = HttpClient.newBuilder().build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(TRANSFER_RECIPIENT_URL))
                    .header("Authorization","Bearer "+ API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), TransferRecipientResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new ConnectionErrorException("An error occurred: "+e);
        }

    }

    @Override
    public TransferRefRequest generateTransferRef(TransferRefRequest request) {
        UUID uuid = UUID.randomUUID();
        request.setReference(uuid.toString());
        return request;
    }

    @Override
    public InitiateTransferResponse initiateTransfer(TransferRefRequest transferRefRequest) {
        if (transferRefRequest.getReference() == null)throw new MissingReferenceException("Transfer reference cannot be null");
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(transferRefRequest);
            HttpClient httpClient = HttpClient.newBuilder().build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                   .uri(URI.create(INITIATE_TRANSFER_URL))
                   .header("Authorization", "Bearer " + API_KEY)
                   .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                   .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), InitiateTransferResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
