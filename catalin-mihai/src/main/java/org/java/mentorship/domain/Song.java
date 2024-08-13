package org.java.mentorship.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class Song {

    private Integer id;

    @NotEmpty(message = "Field 'name' must not be null or empty")
    private String name;

    @NotEmpty(message = "Field 'style' must not be null or empty")
    private String style;

    @NotNull(message = "Field 'duration' must not be null")
    private Integer duration;

    @NotNull(message = "Field 'artistId' must not be null")
    private Integer artistId;

    @NotNull(message = "Field 'albumId' must not be null")
    private Integer albumId;
}
