package org.java.mentorship.validation;

import org.java.mentorship.domain.Album;
import org.java.mentorship.exception.FieldIsNullException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AlbumValidatorTest {

    private static String SOMETHING_STATIC;

    private AlbumValidator albumValidator;

    @BeforeAll
    static void init() {
        SOMETHING_STATIC = "Some static value";
    }

    @BeforeEach
    void initialize() {
        albumValidator = new AlbumValidator();
    }

    @Test
    void validateShouldThrowExceptionWhenIdIsNull() {
        // Given
        Album album = new Album();
        album.setId(null);  // Now this works with Integer

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> albumValidator.validate(album),
                "Expected exception to be thrown");

        assertEquals("Field 'id' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenNameIsNull() {
        // Given
        Album album = new Album();
        album.setId(1);
        album.setName(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> albumValidator.validate(album),
                "Expected exception to be thrown");

        assertEquals("Field 'name' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenArtistIdIsNull() {
        // Given
        Album album = new Album();
        album.setId(1);
        album.setName("Album Name");
        album.setArtistId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> albumValidator.validate(album),
                "Expected exception to be thrown");

        assertEquals("Field 'artistId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldDoNothingWhenAllFieldsAreNotNull() {
        // Given
        Album album = new Album();
        album.setId(1);
        album.setName("Album Name");
        album.setArtistId(1);

        // When & Then
        assertDoesNotThrow(() -> albumValidator.validate(album), "Expected no exception to be thrown");
    }

    @AfterEach
    void destroy() {
        albumValidator = null;
    }

    @AfterAll
    static void destroyFinal() {
        SOMETHING_STATIC = null;
    }
}
