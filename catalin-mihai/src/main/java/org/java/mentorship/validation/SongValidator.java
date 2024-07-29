package org.java.mentorship.validation;

import org.java.mentorship.domain.Song;
import org.java.mentorship.exception.FieldIsNullException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class SongValidator {

    public void validate(final Song song) {
        if (isNull(song.getName())) {
            throw new FieldIsNullException("name");
        }
    }
}
