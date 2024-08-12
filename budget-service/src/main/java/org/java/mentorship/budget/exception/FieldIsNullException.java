package org.java.mentorship.budget.exception;

public class FieldIsNullException extends RuntimeException {

    private final String fieldName;

    public FieldIsNullException(String fieldName) {
        super(String.format("Field '%s' is null", fieldName));
        this.fieldName = fieldName;
    }
}