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

    @Test
    void validateShouldThrowExceptionWhenIdIsNull() {
        // Given
        Song song = new Song();
        song.setId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> songValidator.validate(song),
                "Expected exception to be thrown");

        assertEquals("Field 'id' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenNameIsNull() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> songValidator.validate(song),
                "Expected exception to be thrown");

        assertEquals("Field 'name' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenStyleIsNull() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName("Name");
        song.setStyle(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> songValidator.validate(song),
                "Expected exception to be thrown");

        assertEquals("Field 'style' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenDurationIsNull() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName("Name");
        song.setStyle("Style");
        song.setDuration(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> songValidator.validate(song),
                "Expected exception to be thrown");

        assertEquals("Field 'duration' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenArtistIdIsNull() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName("Name");
        song.setStyle("Style");
        song.setDuration(300);
        song.setArtistId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> songValidator.validate(song),
                "Expected exception to be thrown");

        assertEquals("Field 'artistId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenAlbumIdIsNull() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName("Name");
        song.setStyle("Style");
        song.setDuration(300);
        song.setArtistId(1);
        song.setAlbumId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> songValidator.validate(song),
                "Expected exception to be thrown");

        assertEquals("Field 'albumId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldDoNothingWhenAllFieldsAreNotNull() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName("Name");
        song.setStyle("Style");
        song.setDuration(300);
        song.setArtistId(1);
        song.setAlbumId(1);

        // When & Then
        assertDoesNotThrow(() -> songValidator.validate(song), "Expected no exception to be thrown");
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
