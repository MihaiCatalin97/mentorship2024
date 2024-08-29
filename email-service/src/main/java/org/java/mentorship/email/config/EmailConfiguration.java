package org.java.mentorship.email.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EmailConfiguration {

    @Value("${spring.mail.username}")
    private String username;

}
