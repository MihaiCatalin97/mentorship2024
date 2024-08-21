package org.java.mentorship.contracts.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Integer id;
    private OffsetDateTime expiresAt;
    private Integer userId;
}
