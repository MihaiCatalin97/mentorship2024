package org.java.mentorship.domain;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    private Integer id;

    private String name;

    private String style;

    private Integer duration;

    private Integer artistId;

    private Integer albumId;
}
