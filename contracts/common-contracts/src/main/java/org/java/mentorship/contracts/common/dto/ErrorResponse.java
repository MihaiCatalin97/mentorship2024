package org.java.mentorship.contracts.common.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String error;
    private String service;
    private OffsetDateTime timestamp;

    public ErrorResponse(String error, String service) {
        this.error = error;
        this.service = service;
        this.timestamp = OffsetDateTime.now();
    }
}
