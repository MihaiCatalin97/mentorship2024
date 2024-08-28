package org.java.mentorship.contracts.email.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class EmailRequest {

    @NotNull(message = "Type cannot be null")
    private EmailType type;

    @NotNull(message = "Recipient cannot be null")
    private String to;

    @NotNull(message = "Subject cannot be null")
    private String subject;

}
