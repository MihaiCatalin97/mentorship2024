package org.java.mentorship.contracts.user.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class User {
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;

    private OffsetDateTime createdAt;
    private OffsetDateTime verifiedAt;
    private OffsetDateTime lastSentVerificationNotification;
    private OffsetDateTime lastChangedPassword;
    private OffsetDateTime lastSentPasswordChangeToken;
}
