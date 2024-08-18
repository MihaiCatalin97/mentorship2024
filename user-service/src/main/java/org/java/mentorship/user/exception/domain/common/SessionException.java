package org.java.mentorship.user.exception.domain.common;

import org.springframework.http.HttpStatus;

public class SessionException extends UserServiceException {
    public SessionException(final HttpStatus status, final String message) {
        super(status, message);
    }
}
