package org.java.mentorship.andrei_s.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.java.mentorship.andrei_s.common.Entity;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Song implements Entity {
    private Integer id;

    private String name;
    private String style;
    private Integer duration;
    private Integer artistId;
    private Integer albumId;

    @Override
    public void validate() {
        Song song = this;
        if (Objects.isNull(song.getName())) {
            throw new FieldIsNullException("name");
        }
        if (Objects.isNull(song.getStyle())) {
            throw new FieldIsNullException("style");
        }
        if (Objects.isNull(song.getAlbumId())) {
            throw new FieldIsNullException("albumId");
        }
        if (Objects.isNull((song.getArtistId()))) {
            throw new FieldIsNullException("artistId");
        }
        if (Objects.isNull(song.getDuration())) {
            throw new FieldIsNullException("duration");
        }
    }
}