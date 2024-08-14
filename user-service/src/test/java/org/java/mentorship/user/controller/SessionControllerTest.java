package org.java.mentorship.user.controller;

import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.java.mentorship.user.service.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private SessionController sessionController;

    @Test
    void createSessionShouldReturn200WhenCredentialsValid() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("admin@localhost")
                .password("Password").build();

        when(sessionService.createSession(loginRequest)).thenReturn(
                Optional.of(Session.builder().id(123).build())
        );

        ResponseEntity<Session> response = sessionController.createSession(loginRequest);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(123, response.getBody().getId());
    }

    @Test
    void createSessionShouldReturn400WhenCredentialsInvalid() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("admin@localhost")
                .password("Password").build();

        when(sessionService.createSession(loginRequest)).thenReturn(
                Optional.empty()
        );

        ResponseEntity<Session> response = sessionController.createSession(loginRequest);

        assertEquals(400, response.getStatusCode().value());
        assertTrue(Objects.isNull(response.getBody()));
    }
}
