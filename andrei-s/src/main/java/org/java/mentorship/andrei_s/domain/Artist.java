package org.java.mentorship.andrei_s.domain;

import lombok.Data;
import org.java.mentorship.andrei_s.common.Entity;
import org.java.mentorship.andrei_s.exception.AppException;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;
import org.springframework.http.HttpStatus;

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
        if (artist.getName().length() < 2)
            throw new AppException("Name should be 2 characters or more", HttpStatus.BAD_REQUEST);

    }
}