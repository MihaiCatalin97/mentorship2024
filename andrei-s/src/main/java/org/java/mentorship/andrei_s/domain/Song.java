package org.java.mentorship.andrei_s.domain;

import lombok.Data;

@Data
public class Song {
    private int id;

    private String style;
    private int artistId;
    private int albumId;
}