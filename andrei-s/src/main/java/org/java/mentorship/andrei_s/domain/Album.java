package org.java.mentorship.andrei_s.domain;

import lombok.Data;
import org.java.mentorship.andrei_s.common.Entity;
import org.java.mentorship.andrei_s.exception.AppException;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Data
public class Album implements Entity {
    private Integer id;
    private String name;
    private Integer artistId;

    @Override
    public void validate() {
        Album album = this;
        if (Objects.isNull(album.getName())) {
            throw new FieldIsNullException("name");
        }

        if (album.getName().length() < 2) throw new AppException("Name should be 2 characters or more", HttpStatus.BAD_REQUEST);
    }
}
