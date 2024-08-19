package org.java.mentorship.budget.exception.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void constructorShouldSetErrorMessageCorrectly() {
        // Given
        String expectedErrorMessage = "This is an error message.";

        // When
        ErrorResponse errorResponse = new ErrorResponse(expectedErrorMessage);

        // Then
        assertEquals(expectedErrorMessage, errorResponse.getErrorMessage());
    }

    @Test
    void setterShouldUpdateErrorMessage() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse("Initial error message.");
        String updatedErrorMessage = "Updated error message.";

        // When
        errorResponse.setErrorMessage(updatedErrorMessage);

        // Then
        assertEquals(updatedErrorMessage, errorResponse.getErrorMessage());
    }

    @Test
    void shouldHaveProperToStringImplementation() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse("Error message");

        // When
        String result = errorResponse.toString();

        // Then
        assertTrue(result.contains("ErrorResponse"));
        assertTrue(result.contains("errorMessage=Error message"));
    }

    @Test
    void equalsAndHashCodeShouldWorkCorrectly() {
        // Given
        ErrorResponse errorResponse1 = new ErrorResponse("Error message");
        ErrorResponse errorResponse2 = new ErrorResponse("Error message");

        // Then
        assertEquals(errorResponse1, errorResponse2);  // Should be equal based on the same error message
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());  // Hash codes should also be equal
    }

    @Test
    void notEqualsWhenDifferentErrorMessages() {
        // Given
        ErrorResponse errorResponse1 = new ErrorResponse("Error message 1");
        ErrorResponse errorResponse2 = new ErrorResponse("Error message 2");

        // Then
        assertNotEquals(errorResponse1, errorResponse2);  // Different error messages should make them unequal
    }
}
