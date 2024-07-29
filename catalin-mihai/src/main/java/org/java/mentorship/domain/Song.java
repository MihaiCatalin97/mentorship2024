package org.java.mentorship.domain;

import lombok.Data;

@Data
public class Song {

    private Integer id;

    private String name;

    private String style;

    private Integer duration;

    private Integer artistId;

    private Integer albumId;
}
