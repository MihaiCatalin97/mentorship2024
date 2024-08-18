package org.java.mentorship.gateway.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.java.mentorship.gateway.exception.domain.GatewayException;
import org.springframework.http.HttpStatus;

public class FeignHandler implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public GatewayException decode(String methodKey, Response response) {
        String body = response.body().toString();
        try {
            ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);

            return new GatewayException(errorResponse, HttpStatus.valueOf(response.status()));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return new GatewayException(
                    new ErrorResponse(String.format("Unknown service error: '%s'", body), "gateway"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
