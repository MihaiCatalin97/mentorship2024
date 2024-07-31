package org.java.mentorship.andrei_s.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class AppException extends RuntimeException {
    private HttpStatus statusCode;

    public AppException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
