package org.java.mentorship.notification.repository;


import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationChannel;
import org.java.mentorship.notification.domain.enums.NotificationType;
import org.java.mentorship.notification.mapper.NotificationChannelRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@ContextConfiguration(classes = {
        NotificationChannelRepository.class,
        NotificationChannelRowMapper.class
})
@Sql({
        "classpath:schema.sql",
        "classpath:testData.sql"
})
public class NotificationChannelRepositoryTest {

    @Autowired
    private NotificationChannelRepository notificationChannelRepository;

    @Test
    void getNotificationsShouldReturnAllDatabaseEntities() {
        Map<String, Object> params = new HashMap<>();

        List<NotificationChannel> results = notificationChannelRepository.getNotificationsChannels(params);

        assertEquals(1, results.size());
    }

    @Test
    void getNotificationsByIdShouldReturnTheChannelsFromASpecificEntity() {
        List<NotificationChannel> channels = notificationChannelRepository.getNotificationsById(1);

        assertEquals(1, channels.size());
    }

    @Test
    void createShouldAddANewNotificationChannelIntoDatabase() {
        NotificationChannel notificationChannel = NotificationChannel.valueOf("WEB");
        Map<String, Object> params = new HashMap<>();

        notificationChannelRepository.createNotificationChannel(1, notificationChannel);

        List<NotificationChannel> result = notificationChannelRepository.getNotificationsChannels(params);
        assertEquals(2, result.size());
    }

}
