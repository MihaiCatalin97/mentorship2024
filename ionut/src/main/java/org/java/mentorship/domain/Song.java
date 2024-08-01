package org.java.mentorship.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder @Jacksonized
@AllArgsConstructor
public class Song {
    private int id;
    private String style;
    private int artistId;
    private int albumId;
}
