package org.java.mentorship.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
<<<<<<< HEAD

@Data
@Builder
@AllArgsConstructor
public class Song {

    private int id;
    private String style;
    private int artistId;
    private Album album;
=======
import lombok.extern.jackson.Jacksonized;

@Data
@Builder @Jacksonized
@AllArgsConstructor
public class Song {
    private int id;
    private String style;
    private int artistId;
    private int albumId;
>>>>>>> 60fbd81ee044da0ffcdae78b021f97199e1cb409
}
