package com.example.api.application.services;

import com.example.api.application.dto.requests.VerifyPhoneNumberRequest;
import com.example.api.application.ports.input.userUsecases.*;
import com.example.api.application.ports.input.walletUsecases.CreateWalletUseCase;
import com.example.api.application.ports.output.UserOutputPort;
import com.example.api.application.ports.output.WalletOutputPort;
import com.example.api.domain.exceptions.UserExistsException;
import com.example.api.domain.exceptions.UserNotFoundException;
import com.example.api.domain.exceptions.VerificationFailedException;
import com.example.api.domain.models.User;
import com.example.api.domain.models.Wallet;
import com.example.api.infrastructure.adapter.input.rest.dtos.AuthUser;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.AuthLoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.LoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase, FindUserUseCase, UpdateUserUseCase, LoginUserUseCase, ListUsersUseCase {
    private UserOutputPort userOutputPort;
    private WalletOutputPort walletOutputPort;
    PasswordEncoder passwordEncoder;
    AuthService authServer;
    private final IdentityVerificationService verificationService;

    @Override
    @Transactional
    public User register(User user) {
        verifyUserIdentity(user.getPhoneNumber());
        checkUserExists(user.getEmail());
        registerAuthUser(user);
        encryptPassword(user);
        Wallet wallet = walletOutputPort.saveWallet(user.createWallet());
        user.setWallet(wallet);
        return userOutputPort.saveUser(user);
    }

    private void verifyUserIdentity(String phoneNumber) {
        VerifyPhoneNumberRequest request = new VerifyPhoneNumberRequest();
        request.setNumber(phoneNumber);
        var response = verificationService.verifyPhoneNumber(request);
        System.out.println(response);
        if (response.statusCode() != 200) {
            log.error("Failed to verify phone number, response code: {}, body: {}", response.statusCode(), response.body());
            throw new VerificationFailedException(String.format("Verification failed %s", response.statusCode()));
        }

    }

    private AuthUser map(User user) {
        AuthUser authUser = new AuthUser();
        authUser.setEmail(user.getEmail());
        authUser.setPassword(user.getPassword());
        authUser.setFirstName(user.getFirstName());
        authUser.setLastName(user.getLastName());
        authUser.setUsername(user.getEmail());
        authUser.setRole(user.getRole());
        return authUser;
    }

    private void registerAuthUser(User user) {
        AuthUser authUser = map(user);
        authServer.createUser(authUser);
    }

    private void encryptPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }


    private void checkUserExists(String email) {
        User user = userOutputPort.getUser(email);
        if (user != null) {
            throw new UserExistsException("User with the email already exist");
        }
    }


    @Override
    public User getUser(String email) {
        User user = userOutputPort.getUser(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User getUser(Long userId) {
        return userOutputPort.getUser(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", userId)));

    }

    @Override
    public User updateUser(User updateUserRequest) {
        User existingUser = getUser(updateUserRequest.getId());
        existingUser.updateUserWith(updateUserRequest);
        if (updateUserRequest.getPassword() != null)
            existingUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        return userOutputPort.saveUser(existingUser);
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        AuthLoginRequest authLoginRequest = new AuthLoginRequest();
        authLoginRequest.setUsername(loginRequest.getEmail());
        authLoginRequest.setPassword(loginRequest.getPassword());
        LoginResponse accessTokenResponse = authServer.loginUser(authLoginRequest);
        return new ModelMapper().map(accessTokenResponse, LoginResponse.class);

    }

    @Override
    public List<User> getAllUsers() {
        return userOutputPort.getAllUsers();
    }
}
