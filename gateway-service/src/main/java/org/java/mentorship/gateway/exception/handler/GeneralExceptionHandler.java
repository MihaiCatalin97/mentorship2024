package org.java.mentorship.gateway.exception.handler;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.java.mentorship.gateway.exception.domain.GatewayNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler(GatewayException.class)
    public ResponseEntity<ErrorResponse> handle(final GatewayException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(exception.getErrorResponse());
    }

// I don't know why this is thrown and why the FeignHandler doesn't handle it.
//    @ExceptionHandler(FeignException.NotFound.class)
//    public ResponseEntity<ErrorResponse> handle() {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new GatewayNotFoundException().getErrorResponse());
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(final RuntimeException exception, final HttpServletRequest request) {
        log.error(String.format("[%s] Caught exception: %s (%s)",
                request.getRequestURI(),
                exception.getMessage(),
                exception.getClass()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GatewayErrorResponse("Unknown service error."));
    }

}