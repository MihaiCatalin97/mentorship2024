package org.java.mentorship.user.domain;

import lombok.Data;
import org.java.mentorship.contracts.user.dto.User;

@Data
public class UserEntity {
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;
    private Boolean verified;
    private String hashedPassword;
    private String verificationToken;

}
