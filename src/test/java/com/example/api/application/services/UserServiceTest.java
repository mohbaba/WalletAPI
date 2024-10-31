package com.example.api.application.services;

import com.example.api.domain.exceptions.UserExistsException;
import com.example.api.domain.exceptions.UserNotFoundException;
import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.input.rest.dtos.requests.LoginRequest;
import com.example.api.infrastructure.adapter.input.rest.dtos.responses.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    @Test
    void userCanBeCreated() {

    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setFirstName("Ajax");
        user.setLastName("Don");
        user.setEmail("ajax.don@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("08155531802");
        User response = userService.register(user);
        assertNotNull(response.getId());
        assertTrue(passwordEncoder.matches("password123", response.getPassword()));

        assertNotNull(response.getWallet());
    }

    @Test
    void registerUser_ShouldThrowUserExistsException_WhenUserAlreadyExists() {
        User user = new User();
        user.setEmail("existing@example.com");
        user.setPassword("password");
        user.setPhoneNumber("08155531802");

        userService.register(user);

        assertThrows(UserExistsException.class, () -> userService.register(user));
    }

    @Test
    void testUpdateUser() {
        User updateUserRequest = new User();
        updateUserRequest.setId(100L);
        updateUserRequest.setEmail("johnAbraham.doe@example.com");
        updateUserRequest.setPassword("newhashedpassword");

        User updatedUser = userService.updateUser(updateUserRequest);

        assertEquals("johnAbraham.doe@example.com", updatedUser.getEmail(), "check if email is correctly updated");
        assertNotEquals("newhashedpassword", updatedUser.getPassword(), "Check if password is hashed"); // Password should be hashed
    }

    @Test
    void getUser_ShouldReturnUser_WhenUserExists() {
        User user = new User();
        user.setEmail("test2@example.com");
        user.setPassword("password");
        user.setPhoneNumber("08155531802");

        userService.register(user);

        User fetchedUser = userService.getUser("test2@example.com");

        assertNotNull(fetchedUser);
        assertEquals("test2@example.com", fetchedUser.getEmail());
    }

    @Test
    void getUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> userService.getUser("nonexistent@example.com"));
    }

    @Test
    void updateUser_ShouldUpdateExistingUser_WhenUserExists() {
        User user = new User();
        user.setEmail("test3@example.com");
        user.setPassword("password");
        user.setPhoneNumber("08155531802");
        User savedUser = userService.register(user);

        savedUser.setPassword("newpassword");

        User updatedUser = userService.updateUser(savedUser);

        assertFalse(passwordEncoder.matches("password", updatedUser.getPassword()));
        assertTrue(passwordEncoder.matches("newpassword", updatedUser.getPassword()));
    }

    @Test
    void updateUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        User user = new User();
        user.setId(999L);
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));
    }

    @Test
    void testLoginUser(){
//        User user = new User();
//        user.setEmail("test@mail.com");
//        user.setLastName("Ajay");
//        user.setFirstName("Alok");
//        user.setPassword("password");
//        user.setPhoneNumber("08155531802");
//        userService.register(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("hashedpassword1");
        LoginResponse loggedInUser = userService.login(loginRequest);
        assertNotNull(loggedInUser);
        assertNotNull(loggedInUser.getAccessToken());
        assertNotNull(loggedInUser.getRefreshToken());
        assertTrue(loggedInUser.getExpiresIn() > 0);
        assertTrue(loggedInUser.getRefreshExpiresIn() > 0);
    }
}
