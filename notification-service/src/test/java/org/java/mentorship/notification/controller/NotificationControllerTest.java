package org.java.mentorship.notification.controller;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationType;
import org.java.mentorship.notification.mapper.NotificationContractMapper;
import org.java.mentorship.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        this.notificationController = new NotificationController(notificationService, new NotificationContractMapper());
    }

    private List<NotificationEntity> notifications = List.of(new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.OVER_SPENDING, Map.of("firstName", "Sorana"), true, OffsetDateTime.now()));

    @Test
    void getNotificationsShouldReturnContractsFromNotificationService() {

        when(notificationService.getNotifications(any())).thenReturn(notifications);

        ResponseEntity<List<Notification>> response = notificationController.getNotifications(2, "a@gmail.com", null, NotificationType.OVER_SPENDING, true);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

//    @Test
//    void getNotificationsShouldReturnContractsFromNotificationServiceWhereChannelsAreNotNull() {
//            // Given
//            Integer userId = null;
//            String email = null;
//        org.java.mentorship.notification.domain.enums.NotificationChannel channelEntity = org.java.mentorship.notification.domain.enums.NotificationChannel.WEB;
//            NotificationChannel channel = NotificationChannel.WEB;
//            NotificationType type = NotificationType.OVER_SPENDING;
//            Boolean marked = true;
//
//            NotificationEntity notificationEntity = new NotificationEntity();
//            notificationEntity.setId(1);
//            notificationEntity.setChannels(Collections.singletonList(channelEntity));
//
//            Notification notification = new Notification();
//            notification.setId(1);
//            notification.setChannels(Collections.singletonList(channel));
//
//            when(notificationService.getNotifications(any(Map.class))).thenReturn(Arrays.asList(notificationEntity));
//            when(notificationContractMapper.map(notificationEntity)).thenReturn(notification);
//
//            // When
//            ResponseEntity<List<Notification>> response = notificationController.getNotifications(userId, email, channelEntity, type, marked);
//
//            // Then
//            assertEquals(200, response.getStatusCodeValue());
//            List<Notification> notifications = response.getBody();
//            assertEquals(1, notifications.size());
//            assertEquals(1, notifications.get(0).getId());
//            assertEquals(channel, notifications.get(0).getChannels().get(0));
//    }

    @Test
    void createNotificationShouldReturnContractAndPostFromNotificationService() {
        when(notificationService.createNotification(any())).thenReturn(notifications.get(0));

        ResponseEntity<Notification> response = notificationController.postNotification(new Notification(1, 2, "a@gmail.com", null, org.java.mentorship.contracts.notification.dto.NotificationType.OVER_SPENDING, Map.of("firstName", "Sorana"), true, OffsetDateTime.now()));

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getUserId());
    }

    @Test
    void updateNotificationShouldReturnContractAndUpdateFromNotificationService() {
        when(notificationService.updateNotification(any(), any())).thenReturn(notifications.get(0));

        ResponseEntity<Notification> response = notificationController.putNotification(1, new Notification(1, 2, "a@gmail.com", null, org.java.mentorship.contracts.notification.dto.NotificationType.OVER_SPENDING, Map.of("firstName", "Sorana"), true, OffsetDateTime.now()));

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getUserId());
    }

    @Test
    void getNotificationByIdShouldReturnContractFromNotificationServiceById() {
        when(notificationService.getById(any())).thenReturn(notifications.get(0));

        ResponseEntity<Notification> response = notificationController.getNotificationById(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getUserId());

    }

    @Test
    void postMarkAsReadNotificationShouldReturnContractFromNotificationService() {
        when(notificationService.markAsRead(any())).thenReturn(notifications.get(0));

        ResponseEntity<Notification> response = notificationController.postNotificationMarkAsRead(1, new Notification());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getUserId());
    }

    @Test
    void deleteNotificationsShouldReturnABooleanIfNotifiactionIsDeleted() {
        when(notificationService.deleteNotification(any())).thenReturn(true);

        ResponseEntity<Boolean> response = notificationController.deleteNotification(any());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
    }


}
