package org.java.mentorship.notification.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.notification.exception.domain.NotificationServiceError;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCheckpointRestore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.notification.exception.domain.NotificationServiceError;

@RestControllerAdvice
public class ValidatorHandler {
    @ExceptionHandler
    public ResponseEntity<NotificationServiceError> handleException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                new NotificationServiceError(ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage())
        );
    }
}