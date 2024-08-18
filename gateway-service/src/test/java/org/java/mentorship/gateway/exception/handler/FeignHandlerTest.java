package org.java.mentorship.gateway.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Body;
import feign.Response;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.gateway.config.JacksonConfig;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JacksonConfig.class)
class FeignHandlerTest {
    @Autowired
    private ObjectMapper objectMapper;

    private FeignHandler feignHandler;

    @BeforeEach
    void setUp() {
        feignHandler = new FeignHandler(objectMapper);
    }

    @Test
    void decodeShouldReturnException() throws JsonProcessingException {
        // Given
        String errorResponse = objectMapper.writeValueAsString(new ErrorResponse("Error", "gateway"));
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
