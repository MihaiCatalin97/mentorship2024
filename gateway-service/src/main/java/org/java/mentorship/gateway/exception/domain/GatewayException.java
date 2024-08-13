package org.java.mentorship.gateway.exception.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class GatewayException extends RuntimeException {

    private final HttpStatus statusCode;

    public GatewayException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
