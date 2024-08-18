package org.java.mentorship.contracts.common.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@Builder
public class ErrorResponse {
    private final String error;
    private final String service;
    private final OffsetDateTime timestamp;

    public ErrorResponse(String error, String service) {
        this.error = error;
        this.service = service;
        this.timestamp = OffsetDateTime.now();
    }
}
