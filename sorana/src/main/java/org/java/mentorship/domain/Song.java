package org.java.mentorship.domain;

import lombok.Data;

@Data
public class Song {

    private int id;
    private String style;
    private int artistId;
    private int albumId;

    public Song(int id, String style, int artistId, int albumId) {
        this.id = id;
        this.style = style;
        this.artistId = artistId;
        this.albumId = albumId;
    }
}
