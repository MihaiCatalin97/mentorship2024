package org.java.mentorship.user.exception.handler;

import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.contracts.user.dto.error.UserServiceError;
import org.java.mentorship.user.exception.domain.common.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceExceptionHandlerTest {

    private final ServiceExceptionHandler handler = new ServiceExceptionHandler();

    @Test
    void handlerShouldHandleValidationException() {
        UserServiceException exception = new UserServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Message");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleException(exception);

        assertEquals(500, responseEntity.getStatusCode().value());
        assertTrue(Objects.nonNull(responseEntity.getBody()));
        assertEquals("Message", responseEntity.getBody().getError());
    }

}
