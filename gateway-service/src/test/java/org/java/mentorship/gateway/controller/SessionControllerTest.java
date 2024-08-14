package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @Mock
    private UserFeignClient userFeignClient;

    @InjectMocks
    private SessionController sessionController;

    @Test
    void createSessionShouldReturnDataFromFeign() {
        when(userFeignClient.createSession(any(LoginRequest.class)))
                .thenReturn(Session.builder().build());

        ResponseEntity<Session> response = sessionController.createSession(new LoginRequest());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getSessionShouldReturnDataFromFeign() {
        when(userFeignClient.getSession(anyString()))
                .thenReturn(Session.builder().build());

        ResponseEntity<Session> response = sessionController.getSession(anyString());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getSessionsByUserShouldReturnDataFromFeign() {
        when(userFeignClient.getSessions(anyInt()))
                .thenReturn(Collections.singletonList(Session.builder().build()));

        ResponseEntity<List<Session>> response = sessionController.getSessionsByUser(anyInt());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getActiveSessionsByUserShouldReturnDataFromFeign() {
        when(userFeignClient.getActiveSessions(anyInt()))
                .thenReturn(Collections.singletonList(Session.builder().build()));

        ResponseEntity<List<Session>> response = sessionController.getActiveSessionsByUser(anyInt());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
}

