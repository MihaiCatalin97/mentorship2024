package org.java.mentorship.calculation.tasks;

import org.java.mentorship.contracts.budget.client.CategoryFeignClient;
import org.java.mentorship.contracts.budget.client.TransactionFeignClient;
import org.java.mentorship.contracts.budget.dto.Category;
import org.java.mentorship.contracts.budget.dto.Transaction;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateWeeklyCashFlowReportTest {

    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private NotificationFeignClient notificationFeignClient;

    @Mock
    private TransactionFeignClient transactionFeignClient;

    @Mock
    private CategoryFeignClient categoryFeignClient;

    @InjectMocks
    private GenerateWeeklyCashFlowReport generateWeeklyCashFlowReport;

    @Test
    void generateWeeklyCashFlowReportShouldGenerateReport() {
        when(userFeignClient.getUsers()).thenReturn(
                Collections.singletonList(
                        User.builder()
                                .id(123)
                                .email("handrei@dfourmusic.com")
                                .build()
                )
        );

        when(categoryFeignClient.getCategories()).thenReturn(
                Collections.singletonList(
                        Category.builder()
                                .id(1)
                                .name("Category")
                                .userId(123)
                                .build()
                )
        );

        when(transactionFeignClient.getTransactions()).thenReturn(
                Collections.singletonList(
                        Transaction.builder()
                                .id(1)
                                .userId(123)
                                .type(TransactionType.EXPENSE)
                                .categoryId(1)
                                .value(123)
                                .build()
                )
        );
        Assertions.assertDoesNotThrow(generateWeeklyCashFlowReport::generateWeeklyCashFlowReport);

        verify(notificationFeignClient).postNotification(any());
    }

}
