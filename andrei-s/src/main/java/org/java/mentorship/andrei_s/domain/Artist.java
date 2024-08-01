package org.java.mentorship.andrei_s.domain;

import lombok.Data;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;

import java.util.Objects;

@Data
public class Artist {
    private Integer id;
    private String name;

    public void validate() {
        Artist artist = this;
        if (Objects.isNull(artist.getName())) {
            throw new FieldIsNullException("name");
        }
    }
}