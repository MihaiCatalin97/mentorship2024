package org.java.mentorship.contracts.user.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UserServiceError {

    public final String message;

}
