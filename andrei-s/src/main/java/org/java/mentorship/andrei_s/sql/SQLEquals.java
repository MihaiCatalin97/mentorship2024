package org.java.mentorship.andrei_s.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.java.mentorship.andrei_s.sql.common.SQLExpression;

@Data
@AllArgsConstructor
public class SQLEquals implements SQLExpression {
    private final String property;
    private final Object value;

    @Override
    public String build() {
        return String.format("%s=%s ", property, value);
    }
}
