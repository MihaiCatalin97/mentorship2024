package org.java.mentorship.validation;

import org.java.mentorship.domain.Album;
import org.java.mentorship.exception.FieldIsNullException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AlbumValidator {
    public void validate(Album album) {
        if (isNull(album.getId())) {
            throw new FieldIsNullException("id");
        }
        if (isNull(album.getName())) {
            throw new FieldIsNullException("name");
        }
    }
}