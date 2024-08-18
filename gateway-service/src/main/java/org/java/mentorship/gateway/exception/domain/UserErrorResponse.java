package org.java.mentorship.gateway.exception.domain;

import org.java.mentorship.contracts.common.dto.ErrorResponse;

public class UserErrorResponse  extends ErrorResponse {
    public UserErrorResponse(String error) {
        super(error, "user");
    }
}
