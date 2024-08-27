package org.java.mentorship.gateway.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.gateway.config.JacksonConfig;
import org.java.mentorship.gateway.exception.domain.GatewayErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.ByteBuffer;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        GeneralExceptionHandler.class,
        JacksonConfig.class
})
class GeneralExceptionHandlerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GeneralExceptionHandler handler;

    @Test
    void handleShouldHandleGatewayException() {
        // Given
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new GatewayErrorResponse("message");

        // When
        ResponseEntity<ErrorResponse> actual = handler.handleGatewayException(new GatewayException(errorResponse, status), Mockito.mock(HttpServletRequest.class));

        // Then
        assertNotNull(actual.getBody());
        assertEquals(errorResponse.getErrors(), actual.getBody().getErrors());
        assertEquals(status, actual.getStatusCode());
    }

    @Test
    void handleShouldHandleGatewayExceptionWithSourceException() {
        // Given
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new GatewayErrorResponse("message");

        // When
        ResponseEntity<ErrorResponse> actual = handler.handleGatewayException(new GatewayException(errorResponse, new RuntimeException(), status), Mockito.mock(HttpServletRequest.class));

        // Then
        assertNotNull(actual.getBody());
        assertEquals(errorResponse.getErrors(), actual.getBody().getErrors());
        assertEquals(status, actual.getStatusCode());
    }

    @Test
    void feignExceptionHandleShouldReturnOriginalErrorResponse() throws JsonProcessingException {
        // Given
        FeignException feignException = mock(FeignException.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse("message", "service");
        when(feignException.responseBody()).thenReturn(
                Optional.of(ByteBuffer.wrap(objectMapper.writeValueAsBytes(errorResponse)))
        );
        when(request.getRequestURI()).thenReturn("/");
        when(feignException.status()).thenReturn(status.value());

        // When
        ResponseEntity<ErrorResponse> actual = handler.handleFeignException(feignException, request);

        // Then
        assertNotNull(actual.getBody());
        assertEquals(errorResponse.getErrors(), actual.getBody().getErrors());
        assertEquals(errorResponse.getService(), actual.getBody().getService());
        assertEquals(errorResponse.getTimestamp(), actual.getBody().getTimestamp());
        assertEquals(status, actual.getStatusCode());
    }

    @Test
    void feignExceptionHandleShouldReturnInvalidResponse() {
        // Given
        FeignException feignException = mock(FeignException.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "invalid json";
        when(feignException.responseBody()).thenReturn(
                Optional.of(ByteBuffer.wrap(message.getBytes()))
        );
        when(feignException.status()).thenReturn(status.value());

        // When
        ResponseEntity<ErrorResponse> actual = handler.handleFeignException(feignException, request);

        // Then
        assertNotNull(actual.getBody());
        assertEquals(message, actual.getBody().getErrors().getFirst());
        assertEquals(status, actual.getStatusCode());
    }

    @Test
    void handleShouldHandleRuntimeExceptionException() {
        // When
        ResponseEntity<ErrorResponse> actual = handler.handleRuntimeException(new RuntimeException(), mock(HttpServletRequest.class));

        // Then
        assertNotNull(actual.getBody());
        assertEquals("Unknown service error.", actual.getBody().getErrors().getFirst());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }
}
