package org.java.mentorship.gateway.exception.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class GatewayException extends RuntimeException {

    private final HttpStatus statusCode;
    private final ErrorResponse errorResponse;

    public GatewayException(ErrorResponse errorResponse, HttpStatus statusCode) {
        super(errorResponse.joinErrors());
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

    public GatewayException(ErrorResponse errorResponse, Exception sourceException, HttpStatus statusCode) {
        super(errorResponse.joinErrors(), sourceException);
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

}
