package org.java.mentorship.andrei_s.domain;

import lombok.Data;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;

import java.util.Objects;

@Data
public class Album {
    private Integer id;
    private String name;

    public static void validate (Album album) {
        if (Objects.isNull(album.getName())) {
            throw new FieldIsNullException("name");
        }
    }
}
