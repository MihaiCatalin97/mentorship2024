package org.java.mentorship.notification.sql;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SQLfindAllTest {

    @Test
    void getSql() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 123);
        String result = SQLfindAll.getSQL(params);
        assertEquals(" WHERE id = ?", result);
    }
}
