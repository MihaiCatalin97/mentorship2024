package org.java.mentorship.validation;

import org.java.mentorship.domain.Artist;
import org.java.mentorship.exception.FieldIsNullException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ArtistValidatorTest {

    private static String SOMETHING_STATIC;

    private ArtistValidator artistValidator;

    @BeforeAll
    static void init() {
        SOMETHING_STATIC = "Some static value";
    }

    @BeforeEach
    void initialize() {
        artistValidator = new ArtistValidator();
    }

    @Test
    void validateShouldThrowExceptionWhenIdIsNull() {
        // Given
        Artist artist = new Artist();
        artist.setId(null);  // Now this works with Integer

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> artistValidator.validate(artist),
                "Expected exception to be thrown");

        assertEquals("Field 'id' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenNameIsNull() {
        // Given
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> artistValidator.validate(artist),
                "Expected exception to be thrown");

        assertEquals("Field 'name' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldDoNothingWhenAllFieldsAreNotNull() {
        // Given
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Name");

        // When & Then
        assertDoesNotThrow(() -> artistValidator.validate(artist), "Expected no exception to be thrown");
    }

    @AfterEach
    void destroy() {
        artistValidator = null;
    }

    @AfterAll
    static void destroyFinal() {
        SOMETHING_STATIC = null;
    }
}
