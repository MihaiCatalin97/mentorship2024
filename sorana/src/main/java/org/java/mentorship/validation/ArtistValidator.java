package org.java.mentorship.validation;

import net.sf.jsqlparser.util.validation.ValidationException;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.exception.FieldIsNullException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ArtistValidator {
    public void validate(Artist artist) {
        if(isNull(artist.getId())){
            throw new FieldIsNullException("id");
        }
        if(isNull(artist.getName())){
            throw new FieldIsNullException("name");
        }
    }
}
