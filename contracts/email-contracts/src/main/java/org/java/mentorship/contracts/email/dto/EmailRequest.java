package org.java.mentorship.contracts.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@Data
public class EmailRequest {

    @NotNull(message = "Type cannot be null")
    private EmailType type;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Payload cannot be null")
    @Size(min = 1, message = "Payload cannot be empty")
    private Map<String, Object> payload;

}
