package org.java.mentorship.contracts.user.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Session {
    private Integer id;
    private String sessionKey;
    private OffsetDateTime expiresAt;
    private Integer userId;
}
