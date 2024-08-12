package org.java.mentorship.contracts.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Session {
    private String sessionKey;
    private LocalDateTime expiresAt;
    private Integer userId;
}
