package org.java.mentorship.andrei_s.domain;

import lombok.Data;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;

import java.util.Objects;

@Data
public class Song {
    private Integer id;

    private String name;
    private String style;
    private Integer duration;
    private Integer artist_id;
    private Integer album_id;

    public static void validate(Song song) {
        if (Objects.isNull(song.getName())) {
            throw new FieldIsNullException("name");
        }
        if (Objects.isNull(song.getStyle())) {
            throw new FieldIsNullException("style");
        }
        if (Objects.isNull(song.getAlbum_id())) {
            throw new FieldIsNullException("album_id");
        }
        if (Objects.isNull((song.getArtist_id()))) {
            throw new FieldIsNullException("artist_id");
        }
        if (Objects.isNull(song.getDuration())) {
            throw new FieldIsNullException("duration");
        }
    }
}