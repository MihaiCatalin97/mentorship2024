package org.java.mentorship.andrei_s.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFound extends AppException {
    public EntityNotFound(int entityId, String entityName) {
        super(String.format("Couldn't find '%s' with ID '%s'", entityName, entityId), HttpStatus.NOT_FOUND);
    }
}
