package org.java.mentorship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Jacksonized
@Builder
public class Artist {
    private Integer id;
    private String name;
}
