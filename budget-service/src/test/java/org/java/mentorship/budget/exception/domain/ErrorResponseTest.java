package org.java.mentorship.budget.exception.domain;

import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class ErrorResponseTest {

    @Test
    void constructorShouldSetErrorsCorrectlyFromList() {
        // Given
        List<String> errorList = Arrays.asList("Error 1", "Error 2");

        // When
        ErrorResponse errorResponse = new ErrorResponse(errorList);

        // Then
        assertEquals(errorList, errorResponse.getErrors());
    }

    @Test
    void constructorShouldSetErrorCorrectlyFromSingleString() {
        // Given
        String errorMessage = "Single error message";

        // When
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        // Then
        assertEquals(singletonList(errorMessage), errorResponse.getErrors());
    }

    @Test
    void shouldHaveProperToStringImplementation() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse("Error message");

        // When
        String result = errorResponse.toString();

        // Then
        assertTrue(result.contains("ErrorResponse"));
        assertTrue(result.contains("errors=[Error message]"));
    }

    @Test
    void equalsAndHashCodeShouldWorkCorrectly() {
        // Given
        ErrorResponse errorResponse1 = new ErrorResponse("Error message");
        ErrorResponse errorResponse2 = new ErrorResponse("Error message");

        // Then
        assertEquals(errorResponse1, errorResponse2);
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
    }

    @Test
    void notEqualsWhenDifferentErrorLists() {
        // Given
        ErrorResponse errorResponse1 = new ErrorResponse(Arrays.asList("Error message 1"));
        ErrorResponse errorResponse2 = new ErrorResponse(Arrays.asList("Error message 2"));

        // Then
        assertNotEquals(errorResponse1, errorResponse2);
    }
}
