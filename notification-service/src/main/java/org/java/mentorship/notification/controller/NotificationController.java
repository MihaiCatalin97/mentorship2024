package org.java.mentorship.notification.controller;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications/web")
    public ResponseEntity<List<Notification>> getAllNotifications(Model model) {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PostMapping("/notifications")
    public ResponseEntity<Notification> postNotification(@RequestBody Notification notification) {
       return  ResponseEntity.ok(notificationService.createNotification(notification));
    }

}
