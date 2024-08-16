package org.java.mentorship.gateway.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.gateway.exception.domain.APIErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class GeneralExceptionHandlerTest {

    private final GeneralExceptionHandler handler = new GeneralExceptionHandler();

    @Test
    void handleShouldHandleGatewayException() {
        // Given
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "testMessage";

        // When
        ResponseEntity<APIErrorResponse> actual = handler.handle(new GatewayException(message, status), null);

        // Then
        assertNotNull(actual.getBody());
        assertEquals(message, actual.getBody().getMessage());
        assertEquals(status, actual.getStatusCode());
    }

    @Test
    void handleShouldHandleRuntimeException() {
        // When
        ResponseEntity<APIErrorResponse> actual = handler.handle(new RuntimeException(), mock(HttpServletRequest.class));

        // Then
        assertNotNull(actual.getBody());
        assertEquals("Unknown server error.", actual.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }
}
