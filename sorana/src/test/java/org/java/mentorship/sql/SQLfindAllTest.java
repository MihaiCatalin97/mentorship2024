package org.java.mentorship.sql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

//!!!!!!!!!!!!!

@ExtendWith(MockitoExtension.class)
public class SQLfindAllTest {

    @Test
    void getAllShoulReturnAllEntities(){
        Map<String, Object> parmas = new HashMap<>();
        SQLfindAll.getSQL(parmas);
    }

}
