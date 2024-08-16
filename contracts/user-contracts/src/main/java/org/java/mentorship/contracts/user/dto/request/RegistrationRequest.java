package org.java.mentorship.contracts.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be a valid email")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Password must contain " +
            "a lowercase letter, an uppercase letter, " +
            "a number and be at least 8 characters long.")
    private String password;

    @NotBlank(message = "First name must not be empty")
    @Length(min = 3, max = 32, message = "First name must be between 3 and 32 characters long")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Length(min = 3, max = 32, message = "Last name must be between 3 and 32 characters long")
    private String lastName;
}
