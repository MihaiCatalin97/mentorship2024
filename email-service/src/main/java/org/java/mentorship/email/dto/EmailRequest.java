package org.java.mentorship.email.dto;

import lombok.Data;

@Data
public class EmailRequest {

    private EmailType type;
    private String to;
    private String subject;

}
