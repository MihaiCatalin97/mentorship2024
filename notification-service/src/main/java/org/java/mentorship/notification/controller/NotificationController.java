package org.java.mentorship.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationChannel;
import org.java.mentorship.notification.domain.enums.NotificationType;
import org.java.mentorship.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getWebNotifications(@RequestParam(required = false, name = "id") Integer id,
                                                                  @RequestParam(required = false, name = "userId") Integer user_id,
                                                                  @RequestParam(required = false, name = "email") String email,
                                                                  @RequestParam(required = false, name = "payload") Map<String, String> payload,
                                                                  @RequestParam(required = false, name = "channels") List<NotificationChannel> channels,
                                                                  @RequestParam(required = false, name = "type") List<NotificationType> types,
                                                                  @RequestParam(required = false, name = "marked") Boolean marked,
                                                                  @RequestParam(required = false, name = "createAt") OffsetDateTime createAt) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("userId", user_id);
        map.put("email", email);
        map.put("payload", payload);
        map.put("channels", channels);
        map.put("types", types);
        map.put("marked_as_read", marked);
        map.put("create_at", createAt);
        map.values().removeAll(Collections.singleton(null));

        return ResponseEntity.ok(notificationService.getWebNotifications(map));
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(notificationService.getById(id));
    }

    @PostMapping("/notifications")
    public ResponseEntity<Notification> postNotification(@RequestBody @Valid NotificationEntity notification) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @PutMapping("/notifications/{id}")
    public ResponseEntity<Notification> postNotification(@PathVariable("id") Integer id, @RequestBody @Valid NotificationEntity notification) {
        notification.setId(id);
        return ResponseEntity.ok(notificationService.updateNotification(id, notification));
    }

    @PostMapping("/notifications/{id}/read")
    public ResponseEntity<Notification> postNotificationMarkAsRead(@PathVariable(name = "id")Integer id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Boolean> deleteNotification(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }

}
