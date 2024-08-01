package org.java.mentorship.validation;


import org.java.mentorship.domain.Artist;
import org.java.mentorship.exception.FieldIsNullException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistValidatorTest {

    private ArtistValidator artistValidator = new ArtistValidator();

    @Test
    void validateShouldThrownExceptionWhenArtistIsIsNull(){
        Artist artist = new Artist(1,"travis");
        artist.setId(null);

        FieldIsNullException e = assertThrows(FieldIsNullException.class, () -> artistValidator.validate(artist));
        Assertions.assertEquals("Field 'id' is null", e.getMessage());
    }

    @Test
    void validateShouldThrownExceptionWhenArtistNameIsNull(){
        Artist artist = new Artist(1,"travis");
        artist.setName(null);

        FieldIsNullException e = assertThrows(FieldIsNullException.class, () -> artistValidator.validate(artist));
        Assertions.assertEquals("Field 'name' is null", e.getMessage());
    }
}
