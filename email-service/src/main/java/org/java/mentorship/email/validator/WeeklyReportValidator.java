package org.java.mentorship.email.validator;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WeeklyReportValidator extends AbstractPayloadEmailValidator {

    private static final List<String> EXPECTED_FIELDS = Arrays.asList(
            "firstName",
            "lastName",
            "email",
            "report"
    );

    public WeeklyReportValidator() {
        super(EXPECTED_FIELDS);
    }
}
