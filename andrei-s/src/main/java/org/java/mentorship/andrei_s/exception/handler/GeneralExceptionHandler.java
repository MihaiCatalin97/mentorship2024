package org.java.mentorship.andrei_s.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.java.mentorship.andrei_s.exception.AppException;
import org.java.mentorship.andrei_s.exception.domain.APIErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<APIErrorResponse> handle(final AppException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getMessage()));
    }

}
