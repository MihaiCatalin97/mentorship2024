package org.java.mentorship.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class Song {

    private int id;
    private String style;
    private int artistId;
    private Album album;
}
