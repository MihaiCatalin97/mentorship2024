package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.SessionWithKey;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
importimport org.java.mentorship.contracts.user.dto.Session; org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @Mock
    private SessionFeignClient sessionFeignClient;

    @InjectMocks
    private SessionController sessionController;

    @Test
    void createSessionShouldReturnDataFromFeign() {
        when(sessionFeignClient.createSession(any(LoginRequest.class)))
                .thenReturn(SessionWithKey.builder().build());

        ResponseEntity<SessionWithKey> response = sessionController.createSession(new LoginRequest());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getSessionShouldReturnDataFromFeign() {
        when(sessionFeignClient.getSession(anyString()))
                .thenReturn(Session.builder().build());

        ResponseEntity<Session> response = sessionController.getSession(anyString());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getSessionsShouldReturnDataFromFeign() {
        when(sessionFeignClient.find(1, true)).thenReturn(Collections.singletonList(Session.builder().build()));

        ResponseEntity<List<Session>> response = sessionController.findSessions(1, true);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
}

