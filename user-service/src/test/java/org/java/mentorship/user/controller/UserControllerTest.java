package org.java.mentorship.user.controller;

import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.request.PasswordChangeRequest;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.contracts.user.dto.request.SendPasswordChangeTokenRequest;
import org.java.mentorship.contracts.user.dto.request.SendVerificationTokenRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    private List<UserEntity> users;

    @BeforeEach
    void setupMocks() {
        users = Arrays.asList(
                UserEntity.builder().id(1).email("email2@localhost").build(),
                UserEntity.builder().id(2).email("email@localhost").build()
        );

    }

    @Test
    void getUsersShouldReturnContractsFromUserService() {
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getUsers();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(users.getFirst().getId(), response.getBody().getFirst().getId());
    }

    @Test
    void getUserByIdShouldReturnContractsFromUserService() {
        when(userService.getUserById(anyInt())).thenAnswer(invocation -> {
            Integer user = invocation.getArgument(0);
            return Optional.of(UserEntity.builder().id(user).build());
        });

        ResponseEntity<User> response = userController.getUserById(3);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getId());
    }

    @Test
    void getUserByIdShouldReturn400WhenUserNotFound() {
        when(userService.getUserById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(3);

        assertEquals(404, response.getStatusCode().value());
        assertTrue(Objects.isNull(response.getBody()));
    }

    @Test
    void registerUserShouldCallAndReturnFromUserService() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("email@localhost")
                .password("Secret")
                .firstName("First name")
                .lastName("Last name")
                .build();
        when(userService.registerUser(registrationRequest)).thenReturn(Optional.of(
                UserEntity.builder().id(100).build()
        ));

        ResponseEntity<User> response = userController.registerUser(registrationRequest);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(100, response.getBody().getId());
    }

    @Test
    void verifyUserShouldCallAndReturnFromUserService() {
        when(userService.verifyUserUsingToken("1")).thenReturn(true);

        ResponseEntity<Boolean> response = userController.verifyUser("1");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(true, response.getBody());
    }

    @Test
    void resendNotificationTokenShouldReturnFromService() {
        when(userService.resendVerificationToken(anyInt()))
                .thenReturn(true);

        ResponseEntity<Boolean> response = userController.sendVerificationToken(
                SendVerificationTokenRequest.builder()
                    .userId(1)
                    .build()
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(true, response.getBody());
    }

    @Test
    void requestChangePasswordShouldReturnFromService() {
        when(userService.requestChangePasswordToken(anyInt()))
                .thenReturn(true);

        ResponseEntity<Boolean> response = userController.requestChangePassword(
            SendPasswordChangeTokenRequest.builder()
                    .userId(1)
                    .build()
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(true, response.getBody());
    }

    @Test
    void changePasswordWithTokenShouldReturnFromService() {
        when(userService.changePasswordWithToken(anyString(), any()))
            .thenReturn(true);

        ResponseEntity<Boolean> response = userController.changePasswordWithToken("aaa", PasswordChangeRequest.builder()
                        .password("abc")
                .build());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(true, response.getBody());
    }
}
