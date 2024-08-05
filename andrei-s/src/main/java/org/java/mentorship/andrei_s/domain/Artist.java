package org.java.mentorship.andrei_s.domain;

import lombok.Data;
import org.java.mentorship.andrei_s.common.Entity;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;

import java.util.Objects;

@Data
public class Artist implements Entity {
    private Integer id;
    private String name;

    @Override
    public void validate() {
        Artist artist = this;
        if (Objects.isNull(artist.getName())) {
            throw new FieldIsNullException("name");
        }
    }
}