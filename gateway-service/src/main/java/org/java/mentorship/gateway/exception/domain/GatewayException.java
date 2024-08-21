package org.java.mentorship.gateway.exception.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
public class GatewayException extends RuntimeException {

    private final HttpStatus statusCode;
    private final ErrorResponse errorResponse;
    private final Optional<RuntimeException> sourceException;

    public GatewayException(ErrorResponse errorResponse, HttpStatus statusCode) {
        super(errorResponse.getError());
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
        this.sourceException = Optional.empty();
    }

    public GatewayException(ErrorResponse errorResponse, RuntimeException sourceException, HttpStatus statusCode) {
        super(errorResponse.getError(), sourceException);
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
        this.sourceException = Optional.of(sourceException);
    }

}
