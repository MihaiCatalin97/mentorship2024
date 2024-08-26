package org.java.mentorship.contracts.email.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class EmailRequest {

    @NotNull(message = "to cannot be null")
    private String to;

    @NotNull(message = "subject cannot be null")
    private String subject;

}
