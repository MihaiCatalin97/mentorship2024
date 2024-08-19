package org.java.mentorship.notification.service;


import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationType;
import org.java.mentorship.notification.repository.NotificationChannelRepository;
import org.java.mentorship.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationChannelRepository notificationChannelRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Captor
    private ArgumentCaptor<NotificationEntity> notificationEntityArgumentCaptor;

    @Test
    void getNotificationsShouldReturnAllNotifications() {
        Map<String, Object> params = new HashMap<>();

        when(notificationRepository.getNotifications(params)).thenAnswer(invocationOnMock -> {
            List<NotificationEntity> notifications = List.of(new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now()));

            return notifications;
        });

        List<Notification> result = notificationService.getWebNotifications(params);
        verify(notificationRepository, times(1)).getNotifications(params);
        assertEquals(result.size(), 1);
    }

    @Test
    void updateNotificationShouldModify() {
        Integer notificationId = 1;
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now());

        when(notificationRepository.update(notificationId, notificationEntity)).thenAnswer(invocationOnMock -> {
            notificationEntity.setUserId(3);
            return notificationEntity;
        });

        Notification result = notificationService.updateNotification(notificationId, notificationEntity);
        verify(notificationRepository, times(1)).update(notificationId, notificationEntity);

        assertEquals(result.getId(), notificationId);
    }

    @Test
    void createShoudAddANewNotification() {
        NotificationEntity notification = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now());

        when(notificationRepository.create(notification)).thenAnswer(invocationOnMock -> {
            NotificationEntity expected = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now());
            return expected;
        });

        Notification result = notificationService.createNotification(notification);

        verify(notificationRepository, times(1)).create(notification);
        assertEquals(result.getId(), notification.getId());

    }

    @Test
    void deleteNotificationShouldDeleteANotificationFromDatabase() {
        Integer id = 1;

        when(notificationRepository.deleteNotification(1)).thenReturn(true);

        boolean result = notificationService.deleteNotification(id);

        assertTrue(result);
    }

    @Test
    void getByIdShouldReturnANotification() {
        Integer id = 1;

        when(notificationRepository.getNotificationById(id)).thenReturn(new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now()));

        Notification result = notificationService.getById(id);

        assertEquals(result.getId(), id);

    }

    @Test
    void markedAsReadShouldModifyTheMarkedField() {
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now());

        when(notificationRepository.getNotificationById(1)).thenReturn(notificationEntity);

        notificationService.markAsRead(1);

        verify(notificationRepository, times(1)).update(1, notificationEntity);

        assertEquals(true, notificationEntity.getMarkedAsRead());
    }
}
