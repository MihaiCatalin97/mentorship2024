package org.java.mentorship.contracts.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be a valid email")
    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;
}
