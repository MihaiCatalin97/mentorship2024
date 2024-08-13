package org.java.mentorship.gateway.exception.handler;

import feign.Response;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

class FeignHandlerTest {

    private final FeignHandler feignHandler = new FeignHandler();

    @ParameterizedTest
    @CsvSource({
            "400, 'Bad Request', 400",
            "404, 'Not Found', 404",
            "500, 'Internal Server Error', 500",
            "501, 'Unknown error', 500"
    })
    void decodeShouldReturnException(final Integer inputStatusCode,
                                     final String expectedErrorMessage,
                                     final Integer expectedStatusCode) {
        // Given
        Response response = Mockito.mock(Response.class);
        when(response.status()).thenReturn(inputStatusCode);

        // When
        Exception exception = feignHandler.decode("GET", response);

        // Then
        assertInstanceOf(GatewayException.class, exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedStatusCode, ((GatewayException) exception).getStatusCode().value());
    }
}
