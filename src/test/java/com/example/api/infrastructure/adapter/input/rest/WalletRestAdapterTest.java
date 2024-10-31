package com.example.api.infrastructure.adapter.input.rest;

import com.example.api.application.services.UserService;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.DepositRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.LoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
class WalletRestAdapterTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private LoginResponse response;

    @BeforeEach
    void setUp() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mohbaba@email.com");
        loginRequest.setPassword("hashedpassword1");
        response = userService.login(loginRequest);
    }

    @Test
    void getWallet() throws Exception {
        mockMvc.perform(get("/api/v1/wallets/wallet/john.doe@example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + response.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
        System.out.println(response);
    }


    @Test
    void getBalance()throws Exception {
        mockMvc.perform(get("/api/v1/wallets/balance/john.doe@example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + response.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testInitializeDeposit() throws Exception{
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAmount(new BigDecimal(200));
        depositRequest.setEmailAddress("john.doe@example.com");
        depositRequest.setPassword("hashedpassword1");

        mockMvc.perform(post("/api/v1/wallets/initializeDeposit")
                .contentType(MediaType.APPLICATION_JSON)
               .header("Authorization", "Bearer " + response.getAccessToken())
                .content(new ObjectMapper().writeValueAsString(depositRequest))
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testVerifyPayment() throws Exception{
        String reference = "qfyd18kdxb";
        mockMvc.perform(post("/api/v1/wallets/verifyPayment/" + reference)
                .contentType(MediaType.APPLICATION_JSON)
               .header("Authorization", "Bearer " + response.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testEndpointSecurity() throws Exception{
        String reference = "qfyd18kdxb";
        mockMvc.perform(post("/api/v1/wallets/verifyPayment/" + reference)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized()).andDo(print());
    }
}