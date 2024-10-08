package org.java.mentorship.notification.exception;

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

class ValidatorHandlerTest {
    ValidatorHandler validatorHandler = new ValidatorHandler();

    @Test
    void handlerShouldHandleValidationException() {

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(new FieldError("object", "name", "Id should not be empty")));
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ErrorResponse> responseEntity = validatorHandler.handleException(exception, request);

        assertEquals(400, responseEntity.getStatusCode().value());
        assertTrue(Objects.nonNull(responseEntity.getBody()));
        assertEquals("Id should not be empty", responseEntity.getBody().joinErrors());
    }

}
