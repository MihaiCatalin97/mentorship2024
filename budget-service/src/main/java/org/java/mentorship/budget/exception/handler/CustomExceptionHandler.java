package org.java.mentorship.budget.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.budget.exception.FieldIsNullException;
import org.java.mentorship.budget.exception.domain.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(FieldIsNullException.class)
    public ResponseEntity<ErrorResponse> handle(final FieldIsNullException exception) {
        log.error("Exception of type {} was handled. Exception message: {}", exception.getClass().getSimpleName(),
                exception.getMessage());

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(final RuntimeException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(exception.getMessage()));
    }
}
