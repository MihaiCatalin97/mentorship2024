package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationFeignClient notificationFeignClient;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void getNotificationsShouldReturnDataFromFeign() {
        when(notificationFeignClient.getNotifications(any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(new Notification()));

        ResponseEntity<List<Notification>> response = notificationController.getNotifications(null, null, null, null, null);

        verify(notificationFeignClient).getNotifications(null, null, null, null, null);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void markNotificationAsReadShouldCallFeign() {
        when(notificationFeignClient.markNotificationMarkAsRead(anyInt())).thenReturn(new Notification());

        ResponseEntity<Notification> response = notificationController.markNotificationMarkAsRead(1);

        verify(notificationFeignClient).markNotificationMarkAsRead(1);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
