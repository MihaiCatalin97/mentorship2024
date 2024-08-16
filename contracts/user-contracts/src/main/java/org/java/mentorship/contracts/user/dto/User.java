package org.java.mentorship.contracts.user.dto;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;
    private Boolean verified;
}
