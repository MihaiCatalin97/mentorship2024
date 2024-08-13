package org.java.mentorship.gateway.exception.handler;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.springframework.http.HttpStatus;

public class FeignHandler implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new GatewayException("Bad Request", HttpStatus.BAD_REQUEST);
            case 404 -> new GatewayException("Not Found", HttpStatus.NOT_FOUND);
            case 500 -> new GatewayException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            default -> new GatewayException("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
