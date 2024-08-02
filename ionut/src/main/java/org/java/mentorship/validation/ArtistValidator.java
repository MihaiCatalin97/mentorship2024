package org.java.mentorship.validation;

import org.java.mentorship.domain.Artist;
import org.java.mentorship.exception.FieldIsNullException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ArtistValidator {

    public void validate(final Artist artist) {
        if (isNull(artist.getName())) {
            throw new FieldIsNullException("name");
        }
    }
}
