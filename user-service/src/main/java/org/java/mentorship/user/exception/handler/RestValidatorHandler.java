package org.java.mentorship.user.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestValidatorHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage(), "user")
        );
    }

}
