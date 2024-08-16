package org.java.mentorship.user.exception.domain.common;

import org.springframework.http.HttpStatus;

public class UserException extends UserServiceException {
    public UserException(final HttpStatus status, final String message) {
        super(status, message);
    }
}
