package org.java.mentorship.gateway.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GeneralExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(GatewayException.class)
    public ResponseEntity<ErrorResponse> handleGatewayException(final GatewayException exception, final HttpServletRequest request) {
        log.error("[{}] Caught exception", request.getRequestURI(), exception);
        return ResponseEntity.status(exception.getStatusCode())
                .body(exception.getErrorResponse());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(final FeignException exception, final HttpServletRequest request) {
        ErrorResponse errorResponse = new GatewayErrorResponse("No message");
        String responseString = StandardCharsets.UTF_8.decode(exception.responseBody().get()).toString();

        if (exception.responseBody().isPresent()) {
            try {
                errorResponse = objectMapper.readValue(responseString, ErrorResponse.class);
            } catch (JsonProcessingException e) {
                log.error("[{}] Caught exception when parsing JSON from service error: '{}' {} ({})",
                        request.getRequestURI(), responseString, exception.getMessage(), exception.getClass(), exception);
                errorResponse = new GatewayErrorResponse(responseString);
            }
        }

        log.error("[{}] Caught {} service error: {}",
                request.getRequestURI(), errorResponse.getService(), errorResponse.getErrors());
        return ResponseEntity.status(exception.status())
                .body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException exception, final HttpServletRequest request) {
        log.error(String.format("[%s] Caught exception: %s (%s)",
                request.getRequestURI(),
                exception.getMessage(),
                exception.getClass()), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GatewayErrorResponse("Unknown service error."));
    }

}