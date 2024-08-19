package org.java.mentorship.notification.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.h2.tools.Server;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationType;
import org.java.mentorship.notification.mapper.NotificationRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@ContextConfiguration(classes = {
        NotificationRepository.class,
        NotificationRowMapper.class
})
@Sql({
        "classpath:schema.sql",
        "classpath:testData.sql"
})
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void getNotificationsShouldReturnAllDatabaseEntities() {
        Map<String, Object> params = new HashMap<>();

        List<NotificationEntity> results = notificationRepository.getNotifications(params);

        assertEquals(1, results.size());
    }

    @Test
    void createShouldAddANewEntityInDatabase() {
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName","fname"), true, OffsetDateTime.now());
        Map<String, Object> params = new HashMap<>();

        notificationRepository.create(notificationEntity);

        List<NotificationEntity> result = notificationRepository.getNotifications(params);
        assertEquals(2, result.size());
    }

    @Test
    void deleteShouldRemoveANewEntityFromDatabase() {
        boolean result = notificationRepository.deleteNotification(1);
        assertTrue(result);
    }

}
