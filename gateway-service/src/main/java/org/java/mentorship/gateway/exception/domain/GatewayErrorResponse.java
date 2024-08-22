package org.java.mentorship.gateway.exception.domain;

import org.java.mentorship.contracts.common.dto.ErrorResponse;

public class GatewayErrorResponse extends ErrorResponse {
    public GatewayErrorResponse(String error) {
        super(error, "gateway");
    }
}
