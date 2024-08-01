package org.java.mentorship.andrei_s.domain;

import org.java.mentorship.andrei_s.exception.FieldIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AlbumTest {

    private Album album;

    @BeforeEach
    void initialize() {
        album = new Album();
        album.setName("Name");
    }

    @Test
    void validateShouldThrowWhenNameIsNull() {
        album.setName(null);
        assertThatThrownBy(() -> album.validate())
                .isInstanceOf(FieldIsNullException.class)
                .hasMessageContaining("Field 'name' is null")
                .extracting("statusCode")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }
}