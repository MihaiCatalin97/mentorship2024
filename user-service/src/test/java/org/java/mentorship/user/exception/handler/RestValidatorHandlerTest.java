package org.java.mentorship.user.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestValidatorHandlerTest {

    private final RestValidatorHandler handler = new RestValidatorHandler();

    @Test
    void handlerShouldHandleValidationException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(
                Collections.singletonList(new FieldError("object", "name", "Name should not be empty"))
        );
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ErrorResponse> responseEntity = handler.handleException(exception, request);

        assertEquals(400, responseEntity.getStatusCode().value());
        assertTrue(Objects.nonNull(responseEntity.getBody()));
        assertEquals("Name should not be empty", responseEntity.getBody().getError());
    }

}
