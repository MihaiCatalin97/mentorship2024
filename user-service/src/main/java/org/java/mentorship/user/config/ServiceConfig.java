package org.java.mentorship.user.config;

import lombok.Generated;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        NotificationFeignClient.class
})
@Generated
public class ServiceConfig {
}
