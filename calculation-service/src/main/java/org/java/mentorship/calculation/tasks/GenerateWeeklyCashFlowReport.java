package org.java.mentorship.calculation.tasks;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.CategoryFeignClient;
import org.java.mentorship.contracts.budget.client.TransactionFeignClient;
import org.java.mentorship.contracts.budget.dto.Category;
import org.java.mentorship.contracts.budget.dto.Transaction;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.User;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GenerateWeeklyCashFlowReport {

    private final UserFeignClient userFeignClient;
    private final NotificationFeignClient notificationFeignClient;
    private final TransactionFeignClient transactionFeignClient;
    private final CategoryFeignClient categoryFeignClient;

    private void generateUserReport(User user) {
        List<Category> categories = categoryFeignClient.getCategories().stream()
                .filter(category -> Objects.equals(category.getUserId(), user.getId()))
                .toList();

        List<String> report = new ArrayList<>();

        categories.forEach(category -> {
            List<Transaction> transactions = transactionFeignClient.getTransactions(null)
                    .stream()
                    .filter(transaction -> Objects.equals(transaction.getUserId(), user.getId()) &&
                            Objects.equals(transaction.getCategoryId(), category.getId()))
                    .toList();

            int sum = transactions.stream()
                    .mapToInt(transaction -> transaction.getType() == TransactionType.EXPENSE
                            ? -transaction.getValue()
                            : transaction.getValue())
                    .sum();

            report.add("Category: " + category.getName() + ", Total: " + sum);
        });

        notificationFeignClient.postNotification(Notification.builder()
                .userId(user.getId())
                .channels(List.of(NotificationChannel.EMAIL, NotificationChannel.WEB))
                .email(user.getEmail())
                .payload(Map.of("report", report, "firstName", user.getFirstName(), "lastName", user.getLastName()))
                .markedAsRead(false)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .type(NotificationType.WEEKLY_REPORT)
                .build());
    }

    @Scheduled(cron = "${tasks.generateWeeklyCashFlowReport.cron}")
    @EventListener(ApplicationReadyEvent.class)
    public void generateWeeklyCashFlowReport() {
        List<User> users = userFeignClient.getUsers();

        users.forEach(this::generateUserReport);
    }

}
