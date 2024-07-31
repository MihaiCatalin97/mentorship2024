package org.java.mentorship.validation;

import net.sf.jsqlparser.util.validation.ValidationException;
import org.java.mentorship.domain.Artist;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ArtistValidator {
    public void validate(Artist artist) {
        if(isNull(artist.getId())){
            throw new ValidationException("Artist id is required");
        }
        if(isNull(artist.getName())){
            throw new ValidationException("Artist name is required");
        }
    }
}
