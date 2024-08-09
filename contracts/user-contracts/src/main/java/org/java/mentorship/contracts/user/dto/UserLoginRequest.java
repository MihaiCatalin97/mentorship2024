package org.java.mentorship.contracts.user.dto;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;
    private String password;

}
