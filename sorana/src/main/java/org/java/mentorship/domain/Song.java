package org.java.mentorship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
@AllArgsConstructor
public class Song {

    private int id;
    private String style;
    private int artistId;
    private int albumId;


}
