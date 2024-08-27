package org.java.mentorship.notification.controller;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationChannel;
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
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private NotificationContractMapper mapper = new NotificationContractMapper();
    private List<NotificationEntity> notifications = List.of(new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.OVER_SPENDING, Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC)));

    @BeforeEach
    void setUp() {
        this.notificationController = new NotificationController(notificationService, mapper);
    }

    @Test
    void getNotificationsShouldReturnContractsFromNotificationService() {

        when(notificationService.getNotifications(any())).thenReturn(notifications);

        ResponseEntity<List<Notification>> response = notificationController.getNotifications(2, "a@gmail.com", null, NotificationType.OVER_SPENDING, true);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void postNotificationsShouldCreateContractFromNotificationServiceWhereChannelsAreNotNull() {
        NotificationEntity notificationEntity = new NotificationEntity(1, 2, "a@gmail.com", null, NotificationType.OVER_SPENDING, Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC));

        when(notificationService.createNotification(notificationEntity)).thenAnswer(invocationOnMock -> {
            notificationEntity.setChannels(List.of(NotificationChannel.WEB));
            return notificationEntity;
        });

        ResponseEntity<Notification> response = notificationController.postNotification(mapper.map(notificationEntity));

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getChannels());
    }

    @Test
    void createNotificationShouldReturnContractAndPostFromNotificationService() {
        when(notificationService.createNotification(any())).thenReturn(notifications.get(0));

        ResponseEntity<Notification> response = notificationController.postNotification(new Notification(1, 2, "a@gmail.com", null, org.java.mentorship.contracts.notification.dto.NotificationType.OVER_SPENDING, Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC)));

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getUserId());
    }

    @Test
    void updateNotificationShouldReturnContractAndUpdateFromNotificationService() {
        when(notificationService.updateNotification(any(), any())).thenReturn(notifications.get(0));

        ResponseEntity<Notification> response = notificationController.putNotification(1, new Notification(1, 2, "a@gmail.com", null, org.java.mentorship.contracts.notification.dto.NotificationType.OVER_SPENDING, Map.of("firstName", "Sorana"), true, OffsetDateTime.now(ZoneOffset.UTC)));

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
