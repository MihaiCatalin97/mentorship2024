package org.java.mentorship.user.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.contracts.user.dto.UserServiceError;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestValidatorHandlerTest {

    private final RestValidatorHandler handler = new RestValidatorHandler();

    @Test
    void handlerShouldHandleValidationException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("object", "name", "Name should not be empty")));
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<UserServiceError> responseEntity = handler.handleException(exception, request);

        assertEquals(400, responseEntity.getStatusCode().value());
        assertTrue(Objects.nonNull(responseEntity.getBody()));
        assertEquals("Name should not be empty", responseEntity.getBody().getMessage());
    }

}
