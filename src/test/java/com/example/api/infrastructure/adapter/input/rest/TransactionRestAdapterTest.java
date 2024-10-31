package com.example.api.infrastructure.adapter.input.rest;

import com.example.api.application.services.UserService;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.LoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
class TransactionRestAdapterTest {
    @Autowired
    private MockMvc mockMvc;
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
    void getTransaction() throws Exception {
        mockMvc.perform(get("/api/v1/transactions/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + response.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());


    }

    @Test
    void getListOfTransactions()throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("irelia@email.com");
        request.setPassword("12345");
        LoginResponse response1 = userService.login(request);
        mockMvc.perform(get("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + response1.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testGetListOfTransactionsForUser() throws Exception {
        mockMvc.perform(get("/api/v1/transactions/user/mohbaba@email.com")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + response.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }@Test
    void testGetListOfTransactionsForUserByDate() throws Exception {
        mockMvc.perform(post("/api/v1/transactions/user/date")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + response.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }
}