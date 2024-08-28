package org.java.mentorship.email.validator;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.email.exception.domain.EmailServiceException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor
public class AbstractPayloadEmailValidator {

    private final List<String> expectedFields;

    public void validate(final Map<String, Object> payload) {
        expectedFields
                .forEach(fieldName -> {
                    if (Objects.isNull(payload.get(fieldName))) {
                        throw new EmailServiceException(BAD_REQUEST, String.format("Invalid payload: field %s is null", fieldName));
                    }
                });
    }
}
