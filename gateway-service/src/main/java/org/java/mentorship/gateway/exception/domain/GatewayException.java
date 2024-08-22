package org.java.mentorship.gateway.exception.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class GatewayException extends RuntimeException {

    private final HttpStatus statusCode;
    private final ErrorResponse errorResponse;

    public GatewayException(ErrorResponse errorResponse, HttpStatus statusCode) {
        super(String.join("; ", errorResponse.getErrors()));
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

    public GatewayException(ErrorResponse errorResponse, Exception sourceException, HttpStatus statusCode) {
        super(String.join("; ", errorResponse.getErrors()), sourceException);
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

}
