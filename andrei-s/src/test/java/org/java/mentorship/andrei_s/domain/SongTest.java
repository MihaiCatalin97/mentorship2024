package org.java.mentorship.andrei_s.domain;

import org.java.mentorship.andrei_s.exception.FieldIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SongTest {

    private Song song;

    @BeforeEach
    void initialize() {
        song = new Song();
        song.setName("Name");
        song.setDuration(160);
        song.setStyle("Style");
        song.setAlbumId(1);
        song.setArtistId(1);
    }

    @Test
    void validateShouldThrowWhenNameIsNull() {
        song.setName(null);
        assertThatThrownBy(() -> song.validate())
                .isInstanceOf(FieldIsNullException.class)
                .hasMessageContaining("Field 'name' is null")
                .extracting("statusCode")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void validateShouldThrowWhenArtistIdIsNull() {
        song.setArtistId(null);
        assertThatThrownBy(() -> song.validate())
                .isInstanceOf(FieldIsNullException.class)
                .hasMessageContaining("Field 'artistId' is null")
                .extracting("statusCode")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void validateShouldThrowWhenAlbumIdIsNull() {
        song.setAlbumId(null);
        assertThatThrownBy(() -> song.validate())
                .isInstanceOf(FieldIsNullException.class)
                .hasMessageContaining("Field 'albumId' is null")
                .extracting("statusCode")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void validateShouldThrowWhenDurationIsNull() {
        song.setDuration(null);
        assertThatThrownBy(() -> song.validate())
                .isInstanceOf(FieldIsNullException.class)
                .hasMessageContaining("Field 'duration' is null")
                .extracting("statusCode")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void validateShouldThrowWhenStyleIsNull() {
        song.setStyle(null);
        assertThatThrownBy(() -> song.validate())
                .isInstanceOf(FieldIsNullException.class)
                .hasMessageContaining("Field 'style' is null")
                .extracting("statusCode")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }
}