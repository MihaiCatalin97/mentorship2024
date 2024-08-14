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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    @Mock
    SessionService sessionService;

    @InjectMocks
    SessionController sessionController;

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
        assertTrue(Objects.nonNull(response.getBody()));
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
        assertFalse(Objects.nonNull(response.getBody()));
    }

    @Test
    void getSessionShouldReturn200WhenSessionExists() {
        when(sessionService.getActiveSession("1")).thenReturn(Optional.of(Session.builder().id(1).build()));

        ResponseEntity<Session> response = sessionController.getSession("1");

        assertEquals(200, response.getStatusCode().value());
        assertTrue(Objects.nonNull(response.getBody()));
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void getSessionShouldReturn404WhenSessionDoesNotExists() {
        when(sessionService.getActiveSession("1")).thenReturn(Optional.empty());

        ResponseEntity<Session> response = sessionController.getSession("1");

        assertEquals(404, response.getStatusCode().value());
        assertFalse(Objects.nonNull(response.getBody()));
    }

    @Test
    void getSessionsByUserIdShouldReturnSessions() {
        when(sessionService.getSessionsByUser(1)).thenReturn(
                Arrays.asList(
                        Session.builder().build(),
                        Session.builder().build()
                )
        );

        ResponseEntity<List<Session>> response = sessionController.getSessionsByUser(1);


        assertEquals(200, response.getStatusCode().value());
        assertTrue(Objects.nonNull(response.getBody()));
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getActiveSessionsByUserIdShouldReturnSessions() {
        when(sessionService.getActiveSessionsByUser(1)).thenReturn(
                Arrays.asList(
                        Session.builder().build(),
                        Session.builder().build()
                )
        );

        ResponseEntity<List<Session>> response = sessionController.getActiveSessionsByUser(1);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(Objects.nonNull(response.getBody()));
        assertEquals(2, response.getBody().size());
    }

}
