package org.java.mentorship.notification.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JacksonConfigTest {
    private JacksonConfig jacksonConfig = new JacksonConfig();

    @Test
    void objectMapperShouldReturnObjectMapper() {
        ObjectMapper objectMapper = jacksonConfig.objectMapper();

        Assertions.assertInstanceOf(ObjectMapper.class, objectMapper);
    }

}
