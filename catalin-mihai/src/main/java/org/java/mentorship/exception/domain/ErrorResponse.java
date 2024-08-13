package org.java.mentorship.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.Collections.singletonList;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private List<String> errors;

    public ErrorResponse(String error) {
        this.errors = singletonList(error);
    }
}
