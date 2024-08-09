package org.java.mentorship.gateway.config;

import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = UserFeignClient.class)
public class GatewayConfiguration {
}
