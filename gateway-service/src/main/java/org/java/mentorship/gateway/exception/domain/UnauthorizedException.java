package org.java.mentorship.gateway.exception.domain;

import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GatewayException {

    public UnauthorizedException(String message) {
        super(new GatewayErrorResponse(message), HttpStatus.UNAUTHORIZED);
    }

}
