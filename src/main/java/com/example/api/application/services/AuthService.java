package com.example.api.application.services;

import com.example.api.application.ports.input.authServerUseCases.*;
import com.example.api.infrastructure.adapter.input.rest.dtos.AuthUser;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AuthLoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AuthRegisterRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Slf4j
public class AuthService implements AuthRegisterUseCase, SendVerificationEmail, DeleteUser, LoginUser, AssignRoleUseCase {
    private final Keycloak keycloak;
    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.token.uri}")
    private String tokenUri;
    @Value("${app.keycloak.admin.clientSecret}")
    private String secret;
    @Value("${app.keycloak.admin.clientId}")
    private String client;

    public AuthService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void createUser(AuthUser authUser) {
        UserRepresentation userRepresentation = mapToUserRepresentationFrom(authUser);

        CredentialRepresentation credentialRepresentation = getCredentialRepresentation(authUser);

        Response response = getResponse(userRepresentation, credentialRepresentation);
        if (!Objects.equals(201, response.getStatus())) {
            log.info("Access denied");
            throw new ForbiddenException("Status code: " + response.getStatus());
        }
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$","$1");
        assignRole(userId, String.valueOf(authUser.getRole()));

    }

    public LoginResponse loginUser(AuthLoginRequest loginRequest) {
        RestTemplate template = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", client);
        params.add("client_secret", secret);
        params.add("username", loginRequest.getUsername());
        params.add("password", loginRequest.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<LoginResponse> response = template.postForEntity(tokenUri, request, LoginResponse.class);
            log.info("Response body: " + response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            log.error("Error during login: "+ex.getMessage());
            throw new AuthenticationServiceException("Authentication failed", ex);
        }
    }


    private Response getResponse(UserRepresentation userRepresentation, CredentialRepresentation credentialRepresentation) {
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        UsersResource usersResource = getUsersResource();
        return usersResource.create(userRepresentation);
    }

    private static CredentialRepresentation getCredentialRepresentation(AuthUser authUser) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(authUser.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        return credentialRepresentation;
    }

    private static UserRepresentation mapToUserRepresentationFrom(AuthUser authUser) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(authUser.getEmail());
        userRepresentation.setEmail(authUser.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(authUser.getFirstName());
        userRepresentation.setLastName(authUser.getLastName());
        return userRepresentation;
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    public String authRegisterUser(AuthRegisterRequest request) {

        return null;
    }

    @Override
    public void sendVerificationEmail(String userId) {
//        UsersResource usersResource = getUsersResource();
//        usersResource.get(userId).sendVerifyEmail();
    }

    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
    }

    @Override
    public void assignRole(String userId, String roleName) {
        UserResource user = getUser(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(representation));
    }

    public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();

        return usersResource.get(userId);
    }

    public RolesResource getRolesResource(){
        return keycloak.realm(realm).roles();
    }
}
