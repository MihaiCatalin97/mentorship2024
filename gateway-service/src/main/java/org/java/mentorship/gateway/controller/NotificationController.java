package org.java.mentorship.gateway.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.java.mentorship.gateway.security.authorization.UserAuthorization.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationFeignClient notificationFeignClient;

    @GetMapping()
    public ResponseEntity<List<Notification>> getNotifications(@RequestParam(required = false, name = "userId") Integer userId,
                                                               @RequestParam(required = false, name = "email") String email,
                                                               @RequestParam(required = false, name = "channel") NotificationChannel channel,
                                                               @RequestParam(required = false, name = "type") NotificationType type,
                                                               @RequestParam(required = false, name = "markedAsRead") Boolean markedAsRead) {
        loggedInAsAnyUser();

        return ResponseEntity.ok(filterResults(notificationFeignClient.getNotifications(userId, email, channel, type, markedAsRead),
                Notification::getUserId));
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<Notification> markNotificationMarkAsRead(@PathVariable(name = "id") Integer id) {
        loggedInAsAnyUser();

        return ResponseEntity.ok(notificationFeignClient.markNotificationMarkAsRead(id));
    }
}