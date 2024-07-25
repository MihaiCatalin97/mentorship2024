package org.java.mentorship.andrei_s.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Song {
    private int id;

    private String style;
    private int artistId;
    private int albumId;
}