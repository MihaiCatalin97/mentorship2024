package org.java.mentorship.andrei_s.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AppException extends RuntimeException {
    private final HttpStatus statusCode;
    private final Exception originalException;

    public AppException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.originalException = null;
    }

    public AppException(String message, Exception originalException) {
        super(message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.originalException = originalException;
    }
}
