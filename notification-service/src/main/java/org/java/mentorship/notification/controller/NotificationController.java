package org.java.mentorship.notification.controller;

import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications/{id}/web")
    public ResponseEntity<List<Notification>> getWebNotifications(@PathVariable(value = "id") Integer userId) {
        return ResponseEntity.ok(notificationService.getWebNotificationsByUser(userId));
    }

    @PostMapping("/notifications")
    public ResponseEntity<Notification> postNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @PostMapping("/notifications/{id}/read")
    public ResponseEntity<Notification> postNotificationMarkAsRead(@PathVariable(name = "id") Integer id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(notificationService.getById(id));
    }

}
