package org.java.mentorship.notification.config;


import org.java.mentorship.contracts.email.client.EmailFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(clients = {
        EmailFeignClient.class
})
public class NotificationServiceConfiguration {
}
