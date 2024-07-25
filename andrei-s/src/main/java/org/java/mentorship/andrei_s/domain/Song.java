package org.java.mentorship.andrei_s.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Song {
    private final int id;

    private String style;
    private int artistId;
    private int albumId;
}