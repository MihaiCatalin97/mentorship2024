package org.java.mentorship.gateway.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayErrorResponse;
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
        ErrorResponse errorResponse = new GatewayErrorResponse("message");

        // When
        ResponseEntity<ErrorResponse> actual = handler.handle(new GatewayException(errorResponse, status), null);

        // Then
        assertNotNull(actual.getBody());
        assertEquals(errorResponse.getError(), actual.getBody().getError());
        assertEquals(status, actual.getStatusCode());
    }

    @Test
    void handleShouldHandleRuntimeException() {
        // When
        ResponseEntity<ErrorResponse> actual = handler.handle(new RuntimeException(), mock(HttpServletRequest.class));

        // Then
        assertNotNull(actual.getBody());
        assertEquals("Unknown service error.", actual.getBody().getError());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }
}
