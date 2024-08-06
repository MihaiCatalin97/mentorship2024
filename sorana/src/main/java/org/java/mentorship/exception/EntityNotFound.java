package org.java.mentorship.exception;

import lombok.Data;

@Data
public class EntityNotFound extends RuntimeException {
    private Integer id;
    private final String fieldName;

    public EntityNotFound(Integer id, String fieldName) {
        super(String.format("Field '%s' is null", fieldName));
        this.id = id;
        this.fieldName = fieldName;
    }

}
