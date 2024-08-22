package org.java.mentorship.notification.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.notification.exception.domain.NotificationServiceError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidatorHandler {
    @ExceptionHandler
    public ResponseEntity<NotificationServiceError> handleException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(
                new NotificationServiceError(errorMessages)
        );
    }
}