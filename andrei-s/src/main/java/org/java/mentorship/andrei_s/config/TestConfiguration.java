package org.java.mentorship.andrei_s.config;

import org.java.mentorship.andrei_s.service.SongService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    Map<String, String> mapOfConfiguration(SongService songService)
    {
        Map<String, String> map = new HashMap<>();

        map.put("prop1", "val1");
        map.put("prop2", "val2");

        return map;
    }

    @Bean
    Map<String, String> secondMapOfConfiguration()
    {
        Map<String, String> map = new HashMap<>();

        map.put("secondProp1", "val1");
        map.put("secondProp2", "val2");

        return map;
    }

}
