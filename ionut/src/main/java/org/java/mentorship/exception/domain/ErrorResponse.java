package org.java.mentorship.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String errorMessage;
}
