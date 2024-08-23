package org.java.mentorship.notification.service;


import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationChannel;
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
import java.time.ZoneOffset;
import java.util.Arrays;
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
    private ArgumentCaptor<Integer> notificationIdCaptor;

    @Captor
    private ArgumentCaptor<NotificationChannel> channelCaptor;

    @Test
    void getNotificationsShouldReturnAllNotifications() {
        Map<String, Object> params = new HashMap<>();

        when(notificationRepository.getNotifications(params)).thenAnswer(invocationOnMock -> {
            List<NotificationEntity> notifications = List.of(new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC)));

            return notifications;
        });

        List<NotificationEntity> result = notificationService.getNotifications(params);
        verify(notificationRepository, times(1)).getNotifications(params);
        assertEquals(result.size(), 1);
    }

    @Test
    void updateNotificationShouldModify() {
        Integer notificationId = 1;
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC));

        when(notificationRepository.update(notificationId, notificationEntity)).thenAnswer(invocationOnMock -> {
            notificationEntity.setUserId(3);
            return notificationEntity;
        });

        NotificationEntity result = notificationService.updateNotification(notificationId, notificationEntity);
        verify(notificationRepository, times(1)).update(notificationId, notificationEntity);

        assertEquals(result.getId(), notificationId);
    }

//    @Test
//    void createShoudAddANewNotification() {
//        NotificationEntity notification = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC));
//
//        when(notificationRepository.create(notification)).thenAnswer(invocationOnMock -> {
//            NotificationEntity expected = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC));
//            List<NotificationChannel> channels = new ArrayList<>();
//            when(notificationChannelRepository.getNotificationsById(1)).thenReturn(channels);
//            expected.setChannels(channels);
//            return expected;
//        });
//
//        NotificationEntity result = notificationService.createNotification(notification);
//
//        verify(notificationRepository, times(1)).create(notification);
//        assertEquals(result.getId(), notification.getId());
//
//    }

    @Test
    public void createShoudAddANewNotification() {
        List<NotificationChannel> channels = Arrays.asList(NotificationChannel.WEB, NotificationChannel.EMAIL);
        NotificationEntity notification = new NotificationEntity();
        notification.setChannels(channels);

        NotificationEntity savedNotification = new NotificationEntity();
        savedNotification.setId(1);
        savedNotification.setChannels(channels);

        when(notificationRepository.create(notification)).thenReturn(savedNotification);

        NotificationEntity result = notificationService.createNotification(notification);

        // Then
        assertEquals(savedNotification, result);
        verify(notificationRepository, times(1)).create(notification);

        verify(notificationChannelRepository, times(2))
                .createNotificationChannel(notificationIdCaptor.capture(), channelCaptor.capture());

        List<Integer> capturedIds = notificationIdCaptor.getAllValues();
        List<NotificationChannel> capturedChannels = channelCaptor.getAllValues();

        assertEquals(2, capturedIds.size());
        assertEquals(2, capturedChannels.size());
        assertEquals(savedNotification.getId(), capturedIds.get(0));
        assertEquals("WEB", String.valueOf(capturedChannels.get(0)));
        assertEquals("EMAIL", String.valueOf(capturedChannels.get(1)));
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

        when(notificationRepository.getNotificationById(id)).thenReturn(new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC)));

        NotificationEntity result = notificationService.getById(id);

        assertEquals(result.getId(), id);

    }

    @Test
    void markedAsReadShouldModifyTheMarkedField() {
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.valueOf("OVER_SPENDING"), Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC));

        when(notificationRepository.getNotificationById(1)).thenReturn(notificationEntity);

        notificationService.markAsRead(1);

        verify(notificationRepository, times(1)).update(1, notificationEntity);

        assertEquals(true, notificationEntity.getMarkedAsRead());
    }
}
