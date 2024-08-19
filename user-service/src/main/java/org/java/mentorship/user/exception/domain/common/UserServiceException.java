package org.java.mentorship.user.exception.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class UserServiceException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
