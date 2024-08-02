package org.java.mentorship.andrei_s.sql;

import org.java.mentorship.andrei_s.sql.common.SQLExpression;
import org.java.mentorship.andrei_s.sql.common.SQLOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SQLConditions implements SQLExpression {
    List<SQLExpression> expressions;

    public SQLConditions(SQLExpression start) {
        this.expressions = new ArrayList<>();
        this.expressions.add(start);
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        expressions.forEach((k) -> stringBuilder.append(k.build()));
        return String.format("(%s) ", stringBuilder.toString());
    }

    public SQLConditions and(SQLExpression expr) {
        expressions.add(new SQLOperator("AND"));
        expressions.add(expr);
        return this;
    }

    public SQLConditions or(SQLExpression expr) {
        expressions.add(new SQLOperator("OR"));
        expressions.add(expr);
        return this;
    }

    public SQLConditions and(List<SQLExpression> exprs) {
        exprs.forEach(this::and);
        return this;
    }

    public SQLConditions andEquals(String key, Object value) {
        return and(new SQLEquals(key, value));
    }

    public SQLConditions andEquals(Map<String, Object> kvs) {
        kvs.forEach(this::andEquals);
        return this;
    }

}
