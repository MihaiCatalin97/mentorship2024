package org.java.mentorship.gateway.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.gateway.exception.domain.APIErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler(GatewayException.class)
    public ResponseEntity<APIErrorResponse> handle(final GatewayException exception, final HttpServletRequest request) {
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