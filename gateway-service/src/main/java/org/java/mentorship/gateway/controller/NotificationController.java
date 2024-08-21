package org.java.mentorship.gateway.controller;


import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationFeignClient notificationFeignClient;

    @GetMapping()
    public ResponseEntity<List<Notification>> getNotifications(@RequestParam(required = false, name = "id") Integer id,
                                                               @RequestParam(required = false, name = "userId") Integer user_id,
                                                               @RequestParam(required = false, name = "email") String email,
                                                               @RequestParam(required = false, name = "payload") Map<String, String> payload,
                                                               @RequestParam(required = false, name = "channels") List<NotificationChannel> channels,
                                                               @RequestParam(required = false, name = "type") List<NotificationType> types,
                                                               @RequestParam(required = false, name = "marked") Boolean marked,
                                                               @RequestParam(required = false, name = "createAt") OffsetDateTime createAt) {
        return ResponseEntity.ok(notificationFeignClient.getNotifications(id, user_id, email, payload, channels, types, marked, createAt));
    }

    @GetMapping("/{id}/read")
    public ResponseEntity<Notification> getNotificationMarkAsRead(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationFeignClient.postNotificationMarkAsRead(id));
    }
}
