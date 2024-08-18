package org.java.mentorship.gateway.exception.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.java.mentorship.contracts.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
public class GatewayNotFoundException extends GatewayException {

    public GatewayNotFoundException() {
        super(new ErrorResponse("Not found", "gateway"), HttpStatus.NOT_FOUND);
    }

}
