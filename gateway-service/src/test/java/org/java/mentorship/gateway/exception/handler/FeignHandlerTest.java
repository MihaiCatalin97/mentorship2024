package org.java.mentorship.gateway.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Body;
import feign.Response;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

class FeignHandlerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FeignHandler feignHandler = new FeignHandler();

    @Test
    void decodeShouldReturnException() throws JsonProcessingException {
        // Given
        String errorResponse = """
        {
            "error": "Error",
            "service": "gateway",
            "timestamp": "2020-01-01T00:00:00.000Z"
        }""";
        Integer statusCode = 404;
        Response response = Mockito.mock(Response.class);
        Response.Body body = Mockito.mock(Response.Body.class);
        when(body.toString()).thenReturn(errorResponse);
        when(response.status()).thenReturn(statusCode);
        when(response.body()).thenReturn(body);

        // When
        GatewayException exception = feignHandler.decode("GET", response);

        // Then
        assertEquals("Error", exception.getErrorResponse().getError());
        assertEquals(statusCode, exception.getStatusCode().value());
    }
}
