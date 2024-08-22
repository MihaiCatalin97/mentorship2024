package org.java.mentorship.user.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestValidatorHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        ex.getBindingResult().getFieldErrors().stream()
                                .map(FieldError::getDefaultMessage).collect(Collectors.toList()),
                        "user")
        );
    }

}
