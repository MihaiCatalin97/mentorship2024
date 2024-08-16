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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    void getSessionsShouldReturnAllSessions() {
        when(sessionService.find(1, true)).thenReturn(
                Collections.singletonList(Session.builder().build())
        );
        ResponseEntity<List<Session>> response = sessionController.getSessions(1, true);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getSessionShouldReturn404WhenSessionNotFound() {
        when(sessionService.getSession(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Session> response = sessionController.getSession("AA");

        assertEquals(404, response.getStatusCode().value());
        assertTrue(Objects.isNull(response.getBody()));
    }

    @Test
    void getSessionShouldReturn200WhenSessionFound() {
        when(sessionService.getSession(anyString())).thenReturn(Optional.of(Session.builder().build()));

        ResponseEntity<Session> response = sessionController.getSession("AA");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}
