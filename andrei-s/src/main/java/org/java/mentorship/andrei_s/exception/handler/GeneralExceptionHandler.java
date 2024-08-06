package org.java.mentorship.andrei_s.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.andrei_s.exception.AppException;
import org.java.mentorship.andrei_s.exception.domain.APIErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<APIErrorResponse> handle(final AppException exception, final HttpServletRequest request) {
        if (!Objects.isNull(exception.getOriginalException())) {
            log.error(String.format("[%s] Caught exception: %s",
                    request.getRequestURI(),
                    exception.getOriginalException().getMessage()));
        }
        return ResponseEntity.status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIErrorResponse> handle(final RuntimeException exception, final HttpServletRequest request) {
        log.error(String.format("[%s] Caught exception: %s",
                request.getRequestURI(),
                exception.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIErrorResponse("Unknown server error."));
    }

}
