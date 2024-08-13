package org.java.mentorship.gateway.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIErrorResponse {

    private String message;

}