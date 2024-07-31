package org.java.mentorship.andrei_s.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFound extends AppException {
    public EntityNotFound(int entityId) {
        super(String.format("Couldn't find entity with ID '%s'", entityId), HttpStatus.NOT_FOUND);
    }
}
