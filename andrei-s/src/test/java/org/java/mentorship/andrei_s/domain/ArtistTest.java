package org.java.mentorship.andrei_s.domain;

import org.java.mentorship.andrei_s.exception.FieldIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArtistTest {

    private Artist artist;

    @BeforeEach
    void initialize() {
        artist = new Artist();
        artist.setName("Name");
    }

    @Test
    void validateShouldThrowWhenNameIsNull() {
        artist.setName(null);
        assertThatThrownBy(() -> artist.validate())
                .isInstanceOf(FieldIsNullException.class)
                .hasMessageContaining("Field 'name' is null")
                .extracting("statusCode")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }
}