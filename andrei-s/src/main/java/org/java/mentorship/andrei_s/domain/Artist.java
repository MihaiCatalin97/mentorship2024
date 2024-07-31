package org.java.mentorship.andrei_s.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized @Builder
@AllArgsConstructor
public class Artist {
    private final Integer id;

    private String name;
}