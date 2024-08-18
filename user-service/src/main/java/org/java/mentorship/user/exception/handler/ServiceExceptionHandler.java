package org.java.mentorship.user.exception.handler;

import org.java.mentorship.user.exception.domain.common.UserServiceException;
import org.springframework.http.ResponseEntity;
import org.java.mentorship.contracts.common.ErrorResponse;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final UserServiceException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getMessage(), "user"));
    }

}
