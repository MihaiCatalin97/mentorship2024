package org.java.mentorship.user.exception.domain;

import org.java.mentorship.user.exception.domain.common.SessionException;
import org.springframework.http.HttpStatus;

public class TooManySessionsException extends SessionException {
    public TooManySessionsException() {
        super(HttpStatus.TOO_MANY_REQUESTS, "User has too many active sessions");
    }
}
