package org.java.mentorship.andrei_s.exception;

import org.springframework.http.HttpStatus;

public class FieldIsNullException extends AppException {
    public FieldIsNullException(String fieldName) {
        super(String.format("Field '%s' is null", fieldName), HttpStatus.BAD_REQUEST);
    }
}
