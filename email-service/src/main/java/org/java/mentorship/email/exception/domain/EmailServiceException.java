package org.java.mentorship.email.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class EmailServiceException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
