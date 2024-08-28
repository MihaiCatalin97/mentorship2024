package org.java.mentorship.calculation.config;

import lombok.Generated;
import org.java.mentorship.contracts.budget.client.BudgetFeignClient;
import org.java.mentorship.contracts.budget.client.CategoryFeignClient;
import org.java.mentorship.contracts.budget.client.TransactionFeignClient;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Generated
@Configuration
@EnableScheduling
@PropertySource("classpath:task.properties")
@EnableFeignClients(clients = {
        NotificationFeignClient.class,
        UserFeignClient.class,
        TransactionFeignClient.class,
        CategoryFeignClient.class
})
public class SpringConfig { }
