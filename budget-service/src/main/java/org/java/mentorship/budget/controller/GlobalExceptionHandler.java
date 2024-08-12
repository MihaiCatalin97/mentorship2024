package org.java.mentorship.budget.controller;

import org.java.mentorship.budget.exception.FieldIsNullException;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FieldIsNullException.class)
    @ResponseBody
    public ResponseEntity<String> handleFieldIsNullException(FieldIsNullException ex) {
        return ResponseEntity.status(BAD_REQUEST).body("Field is null: " + ex.getMessage());
    }

    @ExceptionHandler(NoEntityFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleNoEntityFoundException(NoEntityFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body("Entity not found: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}

