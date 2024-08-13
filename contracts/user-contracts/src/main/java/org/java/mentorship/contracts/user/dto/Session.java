package org.java.mentorship.contracts.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Integer id;
    private String sessionKey;
    private OffsetDateTime expiresAt;
    private Integer userId;
}
