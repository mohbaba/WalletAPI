package com.example.api.infrastructure.adapter.input.rest;

import com.example.api.application.services.UserService;
import com.example.api.domain.models.Role;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.LoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.RegisterUserRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.UpdateUserRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
class UserRestAdapterTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private ObjectMapper objectMapper;
    private LoginResponse response;

    @BeforeEach
    void setUp() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mohbaba@email.com");
        loginRequest.setPassword("hashedpassword1");
        response = userService.login(loginRequest);
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser() throws Exception {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("ajay");
        registerUserRequest.setLastName("baba");
        registerUserRequest.setEmail("ajaybaba@email.com");
        registerUserRequest.setPhoneNumber("08082838283");
        registerUserRequest.setPassword("password");
        registerUserRequest.setRole(Role.USER);
        mockMvc.perform(post("/api/v1/users/public/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerUserRequest))
        ).andExpect(status().isCreated()).andDo(print());

    }

    @Test
    void updateUser() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(100L);
        updateUserRequest.setFirstName("Moh Leader");
        String accessToken = response.getAccessToken();
        mockMvc.perform(patch("/api/v1/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " +accessToken)
                .content(objectMapper.writeValueAsBytes(updateUserRequest))
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getUser() throws Exception{
        mockMvc.perform(get("/api/v1/users/id/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " +response.getAccessToken())
        ).andExpect(status().isOk());
    }

    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/john.doe@example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " +response.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testLoginUser() throws Exception{
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("irelia@email.com");
        loginRequest.setPassword("12345");
        LoginResponse loginResponse = userService.login(loginRequest);
        mockMvc.perform(post("/api/v1/users/public/login")
               .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " +loginResponse.getAccessToken())
               .content(objectMapper.writeValueAsBytes(loginRequest))
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testGetAllUsersWithoutTheCorrectPermissions() throws Exception{

        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + response.getAccessToken())
        ).andExpect(status().isForbidden()).andDo(print());
    }

    @Test
    void testGetAllUsersWithTheCorrectPermissions() throws Exception{
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("irelia@email.com");
        loginRequest.setPassword("12345");
        LoginResponse loginResponse = userService.login(loginRequest);
        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + loginResponse.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void createUserWithAdminRole() throws Exception {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("irelia");
        registerUserRequest.setLastName("ionia");
        registerUserRequest.setEmail("irelia@email.com");
        registerUserRequest.setPhoneNumber("08082838283");
        registerUserRequest.setPassword("12345");
        registerUserRequest.setRole(Role.ADMIN);
        mockMvc.perform(post("/api/v1/users/public/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerUserRequest))
        ).andExpect(status().isCreated()).andDo(print());
        Thread.sleep(10000);
    }
}