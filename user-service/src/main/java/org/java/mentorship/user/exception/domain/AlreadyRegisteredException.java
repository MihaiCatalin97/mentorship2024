package org.java.mentorship.user.exception.domain;

import org.java.mentorship.user.exception.domain.common.UserException;
import org.springframework.http.HttpStatus;

public class AlreadyRegisteredException extends UserException {
    public AlreadyRegisteredException() {
        super(HttpStatus.BAD_REQUEST, "User already registered");
    }
}
