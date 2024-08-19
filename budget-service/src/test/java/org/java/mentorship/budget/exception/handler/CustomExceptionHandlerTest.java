package org.java.mentorship.budget.exception.handler;

import org.java.mentorship.budget.exception.domain.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomExceptionHandlerTest {

    private final CustomExceptionHandler exceptionHandler = new CustomExceptionHandler();

    @Test
    void shouldReturnBadRequestForMethodArgumentNotValidException() {
        // Given
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Mock the behavior of BindingResult to return field errors
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(
                new FieldError("objectName", "field", "Field error message")));

        // When
        ResponseEntity<ErrorResponse> response = exceptionHandler.handle(exception);

        // Then
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Field error message", response.getBody().getErrors().get(0));
    }

    @Test
    void shouldReturnBadRequestForRuntimeException() {
        // Given
        RuntimeException exception = new RuntimeException("Runtime error");

        // When
        ResponseEntity<ErrorResponse> response = exceptionHandler.handle(exception);

        // Then
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Runtime error", response.getBody().getErrors().get(0));
    }
}
