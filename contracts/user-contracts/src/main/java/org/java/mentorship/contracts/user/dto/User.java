package org.java.mentorship.contracts.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;

    private OffsetDateTime createdAt;
    private OffsetDateTime verifiedAt;
    private OffsetDateTime lastChangedPassword;

    private Boolean isAdmin;
}
