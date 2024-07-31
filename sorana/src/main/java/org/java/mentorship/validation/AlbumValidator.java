package org.java.mentorship.validation;

import net.sf.jsqlparser.util.validation.ValidationException;
import org.java.mentorship.domain.Album;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AlbumValidator {
    public void validate(Album album) {
        if(isNull(album.getId())){
            throw new ValidationException("Artist id is required");
        }
        if(isNull(album.getName())){
            throw new ValidationException("Artist name is required");
        }
    }
}