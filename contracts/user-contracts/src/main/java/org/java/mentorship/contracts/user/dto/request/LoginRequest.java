package org.java.mentorship.contracts.user.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
