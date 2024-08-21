package org.java.mentorship.notification.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.tools.Server;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationType;
import org.java.mentorship.notification.mapper.NotificationRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;

@JdbcTest
@ContextConfiguration(classes = {
        NotificationRepository.class,
        NotificationRowMapper.class
})
@Sql({
        "classpath:schema.sql",
        "classpath:testData.sql"
})
@ExtendWith(MockitoExtension.class)
public class NotificationRepositoryTest {

    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    @InjectMocks
    private NotificationRepository notificationRepository;

    @Test
    void getNotificationsShouldReturnAllDatabaseEntities() {
        Map<String, Object> params = new HashMap<>();

        List<NotificationEntity> results = notificationRepository.getNotifications(params);

        assertEquals(1, results.size());
    }

    @Test
    void createShouldAddANewEntityInDatabase() throws JsonProcessingException {
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName","fname"), true, OffsetDateTime.now());
        Map<String, Object> params = new HashMap<>();
        when(objectMapper.writeValueAsString(any())).thenReturn("{\"firstName\":\"ffname\"}");
        notificationRepository.create(notificationEntity);

        List<NotificationEntity> result = notificationRepository.getNotifications(params);
        assertEquals(2, result.size());
    }

    @Test
    void createShouldThrowExceptionIfPayloadCannotBeParsedToJSON() throws JsonProcessingException {
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName","fname"), true, OffsetDateTime.now());

        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        assertThrows(RuntimeException.class, () -> notificationRepository.create(notificationEntity));
    }

    @Test
    void deleteShouldRemoveANewEntityFromDatabase() {
        boolean result = notificationRepository.deleteNotification(1);
        assertTrue(result);
    }

    @Test
    void updateShouldModifyAEntityInDatabase() {
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName","fname"), true, OffsetDateTime.now());

        notificationEntity.setEmail("b@gmail.com");

        NotificationEntity result = notificationRepository.update(1, notificationEntity);

        assertEquals("b@gmail.com", result.getEmail());
    }

    @Test
    void getByIdShouldReturnAnEntityFromDatabase() {
        NotificationEntity notificationEntity = notificationRepository.getNotificationById(1);

        assertEquals("user@example.com", notificationEntity.getEmail());
    }
}
