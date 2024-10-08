package org.java.mentorship.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;

    private OffsetDateTime createdAt;
    private OffsetDateTime verifiedAt;
    private OffsetDateTime lastChangedPassword;

    // Hidden Values
    private String hashedPassword;
    private String passwordChangeToken;
    private String verificationToken;
    private Boolean isAdmin;
}
