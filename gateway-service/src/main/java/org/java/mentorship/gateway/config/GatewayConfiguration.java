package org.java.mentorship.gateway.config;

import lombok.Generated;
import org.java.mentorship.contracts.calculation.client.CalculationFeignClient;
import org.java.mentorship.contracts.budget.client.AccountFeignClient;
import org.java.mentorship.contracts.budget.client.CategoryFeignClient;
import org.java.mentorship.contracts.budget.client.TransactionFeignClient;
import org.java.mentorship.contracts.calculation.client.CalculationFeignClient;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.budget.client.BudgetFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        UserFeignClient.class,
        SessionFeignClient.class,
        BudgetFeignClient.class,
        AccountFeignClient.class,
        TransactionFeignClient.class,
        CategoryFeignClient.class,
        SessionFeignClient.class,
        CalculationFeignClient.class,
        NotificationFeignClient.class
})
@Generated
public class GatewayConfiguration {
}
