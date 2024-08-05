package org.java.mentorship.andrei_s.domain;

import lombok.Data;
import org.java.mentorship.andrei_s.common.Entity;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;

import java.util.Objects;

@Data
public class Album implements Entity {
    private Integer id;
    private String name;

    @Override
    public void validate() {
        Album album = this;
        if (Objects.isNull(album.getName())) {
            throw new FieldIsNullException("name");
        }
    }
}
