package org.java.mentorship.validation;

import org.java.mentorship.domain.Album;
import org.java.mentorship.exception.FieldIsNullException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlbumValidatorTest {

    private AlbumValidator albumValidator = new AlbumValidator();

    @Test
    void validateShouldThrownExceptionWhenAlbumIsIsNull(){
        Album album = new Album(1,"astroworld");
        album.setId(null);

        FieldIsNullException e = assertThrows(FieldIsNullException.class, () -> albumValidator.validate(album));
        Assertions.assertEquals("Field 'id' is null", e.getMessage());
    }

    @Test
    void validateShouldThrownExceptionWhenAlbumNameIsNull(){
        Album album = new Album(1,"travis");
        album.setName(null);

        FieldIsNullException e = assertThrows(FieldIsNullException.class, () -> albumValidator.validate(album));
        Assertions.assertEquals("Field 'name' is null", e.getMessage());
    }
}
