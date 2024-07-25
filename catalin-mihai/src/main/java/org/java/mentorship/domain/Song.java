package org.java.mentorship.domain;

import lombok.Data;

@Data
public class Song {

    private int id;

    private String style;

    private int artistId;

    private Album album;
}
