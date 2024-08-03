package org.java.mentorship.validation;

import org.java.mentorship.domain.Song;
import org.java.mentorship.exception.FieldIsNullException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SongValidatorTest {

    private static String SOMETHING_STATIC;

    private SongValidator songValidator;

    @BeforeAll
    static void init() {
        SOMETHING_STATIC = "Ceva static";
    }

    @BeforeEach
    void initialize() {
        songValidator = new SongValidator();
    }

    /**
     * methodName Should (Return ceva / Throw ceva / Do nothing) When Parameter is something
     */

    @Test
    void validateShouldThrowExceptionWhenNameIsNull() {
        // Given
        Song song = new Song();
        song.setName(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> songValidator.validate(song),
                "Expected exception to be thrown");

        assertEquals("Field 'name' is null",
                exception.getMessage(),
                "Unexpected exception message");
    }

    @Test
    void validateShouldDoNothingWhenNameIsNotNull() {
        // Given
        Song song = new Song();
        song.setName("Not null");

        // When & Then
        assertDoesNotThrow(() -> songValidator.validate(song),
                "Expected exception to be thrown");
    }

    @AfterEach
    void destroy() {
        songValidator = null;
    }

    @AfterAll
    static void destroyFinal() {
        SOMETHING_STATIC = null;
    }
}
