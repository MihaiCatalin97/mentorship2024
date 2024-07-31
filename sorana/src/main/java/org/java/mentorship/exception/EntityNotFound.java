package org.java.mentorship.exception;

public class EntityNotFound extends RuntimeException{
    private final String fieldName;
    public EntityNotFound(int id,String fieldName) {
        super(String.format("Field '%s' is null", fieldName));
        this.fieldName = fieldName;
    }

}
