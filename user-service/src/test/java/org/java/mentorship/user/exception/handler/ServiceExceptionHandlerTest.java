package org.java.mentorship.user.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.contracts.user.dto.UserServiceError;
import org.java.mentorship.user.exception.domain.common.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

public class ServiceExceptionHandlerTest {

    private final ServiceExceptionHandler handler = new ServiceExceptionHandler();

    @Test
    void handlerShouldHandleValidationException() {
        UserServiceException exception = new UserServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Message");

        HttpServletRequest request = mock(HttpServletRequest.class);

        ResponseEntity<UserServiceError> responseEntity = handler.handleException(exception, request);

        assertEquals(500, responseEntity.getStatusCode().value());
        assertTrue(Objects.nonNull(responseEntity.getBody()));
        assertEquals("Message", responseEntity.getBody().getMessage());
    }

}
