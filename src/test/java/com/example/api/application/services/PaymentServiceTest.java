package com.example.api.application.services;

import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.InitiateTransferResponse;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.paymentServiceResponses.TransferRecipientResponse;
import com.example.api.domain.exceptions.MissingReferenceException;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.CreateRecipientRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.RecipientType;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.TransferRefRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;

    @Test
    void initializePayment() {
    }

    @Test
    void verifyPaymentStatus() {
    }

    @Test
    void testCreateRecipient() {
        CreateRecipientRequest createRecipientRequest = new CreateRecipientRequest();
        createRecipientRequest.setType(RecipientType.BANK);
        createRecipientRequest.setName("Baba Muhammad");
        createRecipientRequest.setAccountNumber("0256587444");
        createRecipientRequest.setBankCode("058");
        createRecipientRequest.setCurrency("NGN");


        TransferRecipientResponse response = paymentService.createRecipient(createRecipientRequest);
        System.out.println(response);

        assertNotNull(response, "Response should not be null");
        assertTrue(response.isStatus(), "The status should be true");
        assertEquals("Transfer recipient created successfully", response.getMessage(), "Message should be correct");
        assertNotNull(response.getData(), "Data should not be null");
        assertEquals("NGN", response.getData().getCurrency(), "Currency should be NGN");
        assertEquals("Baba Muhammad", response.getData().getName(), "Name should be Baba Muhammad");
        assertEquals("nuban", response.getData().getType(), "Type should be nuban");

        assertNotNull(response.getData().getDetails(), "Details should not be null");
        assertEquals("0256587444", response.getData().getDetails().getAccount_number(), "Account number should match");
        assertEquals("Guaranty Trust Bank", response.getData().getDetails().getBank_name(), "Bank name should match");
    }

    @Test
    @DisplayName("Test that generateTransferRef generates a unique reference")
    void testGenerateTransferRef() {
        // Given
        TransferRefRequest request = new TransferRefRequest();
        request.setSource("balance");
        request.setReason("Savings");
        request.setAmount(30000.00);
        request.setRecipient("RCP_1a25w1h3n0xctjg");

        TransferRefRequest result = paymentService.generateTransferRef(request);

        assertNotNull(result.getReference(), "Reference should not be null");
        assertDoesNotThrow(() -> UUID.fromString(result.getReference()), "Reference should be a valid UUID");
    }

    @Test
    @DisplayName("Test initiateTransfer with valid request")
    void testInitiateTransfer_withValidRequest() {
        TransferRefRequest validRequest = new TransferRefRequest();
        validRequest.setAmount(30000.00);
        validRequest.setRecipient("RCP_1a25w1h3n0xctjg");
        validRequest.setReason("Savings");

        validRequest = paymentService.generateTransferRef(validRequest);
        InitiateTransferResponse mockResponse = new InitiateTransferResponse();
        mockResponse.setStatus(true);
        mockResponse.setMessage("Transfer has been queued");

        InitiateTransferResponse response = paymentService.initiateTransfer(validRequest);
        assertNotNull(mockResponse);
        assertTrue(mockResponse.isStatus());
        assertEquals("Transfer has been queued", response.getMessage());
    }

    @Test
    @DisplayName("Test initiateTransfer with missing reference")
    void testInitiateTransfer_withMissingReference() {
        TransferRefRequest invalidRequest = new TransferRefRequest();
        invalidRequest.setAmount(30000.00);
        invalidRequest.setRecipient("RCP_1a25w1h3n0xctjg");
        invalidRequest.setReason("Savings");

        Exception exception = assertThrows(MissingReferenceException.class, () -> {
            paymentService.initiateTransfer(invalidRequest);
        });

        String expectedMessage = "Transfer reference cannot be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test initiateTransfer with invalid recipient")
    void testInitiateTransfer_withInvalidRecipient() {
        TransferRefRequest validRequest = new TransferRefRequest();
        validRequest.setAmount(30000.00);
        validRequest.setRecipient("INVALID_RECIPIENT");
        validRequest.setReason("Savings");

        validRequest = paymentService.generateTransferRef(validRequest);
        InitiateTransferResponse mockResponse = new InitiateTransferResponse();
        mockResponse.setStatus(false);
        mockResponse.setMessage("Invalid recipient");

        InitiateTransferResponse response = paymentService.initiateTransfer(validRequest);
        assertNotNull(mockResponse);
        assertFalse(mockResponse.isStatus());
        assertEquals("Invalid recipient", response.getMessage());
    }

}