package org.java.mentorship.notification.sql;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.StringJoiner;

@UtilityClass
public class SQLfindAll {
    public static String getSQL(Map<String, Object> params) {
        StringJoiner joiner = new StringJoiner(" AND ", " WHERE ", "");

        params.forEach((key, value) ->
                joiner.add(key + " = ?"));

        return (params.size() > 0 ? joiner.toString() : "");
    }
}
