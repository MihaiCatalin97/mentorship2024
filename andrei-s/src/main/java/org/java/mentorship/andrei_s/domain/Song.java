package org.java.mentorship.andrei_s.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized @Builder
@AllArgsConstructor
public class Song {
    private final int id;

    private String style;
    private int artistId;
    private int albumId;
}