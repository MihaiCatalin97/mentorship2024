package org.java.mentorship.sql;

import java.util.Map;
import java.util.StringJoiner;

public class SQLfindAll {

    public static String  getSQL(Map<String, Object> params){
        StringJoiner joiner = new StringJoiner(" AND "," WHERE ","");

        params.forEach((key, value) ->
                joiner.add(key + " = ?"));

        Object[] arams = params.values().toArray();
        return (params.size() > 0 ? joiner.toString() : "");
    }
}
