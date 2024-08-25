package org.java.mentorship.calculation.config;

import lombok.Generated;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Generated
@Configuration
@EnableScheduling
@PropertySource("classpath:task.properties")
public class SpringConfig {
}
