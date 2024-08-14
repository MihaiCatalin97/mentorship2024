package org.java.mentorship.user.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.java.mentorship.contracts.user.dto.UserServiceError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestValidatorHandler {

    @ExceptionHandler
    public ResponseEntity<UserServiceError> handleException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                new UserServiceError(ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage())
        );
    }

}
