package org.java.mentorship.user.exception.domain;

import org.java.mentorship.user.exception.domain.common.UserException;
import org.springframework.http.HttpStatus;

public class WrongPasswordException extends UserException {
    public WrongPasswordException() {
        super(HttpStatus.BAD_REQUEST, "Wrong password supplied for user.");
    }
}
