package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.java.mentorship.contracts.notification.dto.NotificationChannel.EMAIL;
import static org.java.mentorship.contracts.notification.dto.NotificationType.VERIFICATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest extends AbstractControllerTest {

    @MockBean
    private NotificationFeignClient notificationFeignClient;

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void getNotificationsShouldReturnDataFromFeign(final String sessionHeader) throws Exception {
        Notification notification = Notification.builder().id(1).userId(123).build();
        when(notificationFeignClient.getNotifications(any(), any(), any(), any(), any())).thenReturn(singletonList(notification));

        mockMvc.perform(get("/notifications")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void markNotificationAsReadShouldCallFeign(final String sessionHeader) throws Exception {
        Notification notification = Notification.builder().id(1)
                .email("mail@mail.com")
                .channels(singletonList(EMAIL))
                .type(VERIFICATION)
                .payload(singletonMap("firstName", "Test"))
                .markedAsRead(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("UTC")))
                .userId(123).build();
        when(notificationFeignClient.markNotificationMarkAsRead(1)).thenReturn(notification);

        mockMvc.perform(put("/notifications/read/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

}
