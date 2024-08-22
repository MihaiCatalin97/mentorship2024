package org.java.mentorship.contracts.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private List<String> errors;
    private String service;
    private OffsetDateTime timestamp;

    public ErrorResponse(String error, String service) {
        this.errors = Collections.singletonList(error);
        this.service = service;
        this.timestamp = OffsetDateTime.now();
    }

    public ErrorResponse(List<String> errors, String service) {
        this.errors = errors;
        this.service = service;
        this.timestamp = OffsetDateTime.now();
    }

    public String joinErrors() {
        return String.join("; ", errors);
    }
}
