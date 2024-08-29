package org.java.mentorship.email.exception.handler;

import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.email.exception.domain.EmailServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final EmailServiceException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getMessage(), "email"));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final RuntimeException ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage(), "email"));
    }
}
