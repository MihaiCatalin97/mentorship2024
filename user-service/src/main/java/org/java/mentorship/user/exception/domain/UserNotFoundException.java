package org.java.mentorship.user.exception.domain;

import org.java.mentorship.user.exception.domain.common.UserException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User could not be found");
    }
}
