package org.java.mentorship.contracts.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeRequest {

    @NotBlank(message = "Password must not be empty")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Password must contain " +
            "a lowercase letter, an uppercase letter, " +
            "a number and be at least 8 characters long.")
    private String password;

    @NotBlank(message = "Verification token must not be empty")
    private String token;

}
