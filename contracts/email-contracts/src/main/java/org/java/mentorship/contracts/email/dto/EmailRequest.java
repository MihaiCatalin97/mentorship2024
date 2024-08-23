package org.java.mentorship.contracts.email.dto;

import lombok.Data;

@Data
public class EmailRequest {

    private String to;
    private String subject;

}
