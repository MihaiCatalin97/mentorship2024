package org.java.mentorship.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import org.java.mentorship.gateway.exception.handler.FeignHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean @Autowired
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return new FeignHandler(objectMapper);
    }
}