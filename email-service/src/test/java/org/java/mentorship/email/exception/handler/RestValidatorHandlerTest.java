package org.java.mentorship.email.exception.handler;

import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class RestValidatorHandlerTest {

    private RestValidatorHandler handler = new RestValidatorHandler();

    @Test
    void handleExceptionShouldListOfErrors() {
        // Given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "Field is null!");

        when(exception.getBindingResult())
                .thenReturn(bindingResult);
        when(bindingResult.getFieldErrors())
                .thenReturn(singletonList(fieldError));

        // When
        ResponseEntity<ErrorResponse> result = handler.handleException(exception);

        // Then
        assertEquals(BAD_REQUEST, result.getStatusCode());
        assertEquals("email", result.getBody().getService());
        assertNotNull(result.getBody().getTimestamp());
        assertEquals(1, result.getBody().getErrors().size());
        assertEquals("Field is null!", result.getBody().getErrors().get(0));
    }
}
