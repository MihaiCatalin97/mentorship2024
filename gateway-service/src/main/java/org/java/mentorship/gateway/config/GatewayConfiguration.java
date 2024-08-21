package org.java.mentorship.gateway.config;

import org.java.mentorship.contracts.calculation.client.CalculationFeignClient;
import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        UserFeignClient.class,
        SessionFeignClient.class,
        CalculationFeignClient.class
})
public class GatewayConfiguration {
}
