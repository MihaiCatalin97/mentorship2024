package org.java.mentorship.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;
    private Boolean verified;
    private String hashedPassword;
    private String verificationToken;

}
