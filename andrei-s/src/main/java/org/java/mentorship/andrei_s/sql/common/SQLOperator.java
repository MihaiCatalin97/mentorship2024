package org.java.mentorship.andrei_s.sql.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SQLOperator implements SQLExpression {
    private final String operator;

    @Override
    public String build() {
        return operator.toUpperCase() + " ";
    }
}
