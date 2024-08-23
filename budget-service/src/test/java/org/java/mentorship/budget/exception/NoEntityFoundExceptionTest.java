package org.java.mentorship.budget.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NoEntityFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Given
        String expectedMessage = "Entity not found";

        // When
        NoEntityFoundException exception = new NoEntityFoundException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowNoEntityFoundException() {
        // Given
        String expectedMessage = "Entity not found";

        // When & Then
        NoEntityFoundException thrownException = assertThrows(
                NoEntityFoundException.class,
                () -> {
                    throw new NoEntityFoundException(expectedMessage);
                }
        );

        // Assert
        assertEquals(expectedMessage, thrownException.getMessage());
    }
}
