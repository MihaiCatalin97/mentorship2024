package org.java.mentorship.gateway.config;

import feign.codec.ErrorDecoder;
import org.java.mentorship.gateway.exception.handler.FeignHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignHandler();
    }
}