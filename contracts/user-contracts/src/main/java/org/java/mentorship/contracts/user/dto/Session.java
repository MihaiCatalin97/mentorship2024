package org.java.mentorship.contracts.user.dto;

import lombok.Data;

@Data
public class Session {
    private String sessionKey;
    private long expiresAt;
    private Integer userId;
}
