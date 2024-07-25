package org.java.mentorship.config;

import org.java.mentorship.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestConfiguration {

    @Bean
    public Map<String, String> mapOfProperties(TestService service) {
        Map<String, String> result = new HashMap<>();

        result.put("prop1", "val1");
        result.put("prop2", "val2");

        return result;
    }

    @Bean
    public Map<String, String> secondMapOfProperties() {
        Map<String, String> result = new HashMap<>();

        result.put("secondProp1", "val1");
        result.put("secondProp2", "val2");

        return result;
    }
}
