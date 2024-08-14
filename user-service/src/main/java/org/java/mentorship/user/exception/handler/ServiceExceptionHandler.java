package org.java.mentorship.user.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.contracts.user.dto.UserServiceError;
import org.java.mentorship.user.exception.domain.common.UserServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserServiceError> handleException(final UserServiceException ex, final HttpServletRequest request) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new UserServiceError(ex.getMessage()));
    }

}