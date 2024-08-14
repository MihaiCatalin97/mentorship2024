package org.java.mentorship.contracts.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserServiceError {

    public final String message;

}
