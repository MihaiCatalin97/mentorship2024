package org.java.mentorship.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.domain.enums.NotificationChannel;
import org.java.mentorship.notification.domain.enums.NotificationType;
import org.java.mentorship.notification.mapper.NotificationContractMapper;
import org.java.mentorship.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    private final NotificationContractMapper notificationContractMapper;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications(@RequestParam(required = false, name = "userId") Integer userId,
                                                               @RequestParam(required = false, name = "email") String email,
                                                               @RequestParam(required = false, name = "channel") String channel,
                                                               @RequestParam(required = false, name = "type") String type,
                                                               @RequestParam(required = false, name = "marked") Boolean marked
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("email", email);
        map.put("channel", channel);
        map.put("type", type);
        map.put("marked_as_read", marked);
        map.values().removeAll(Collections.singleton(null));

        return ResponseEntity.ok(
                notificationService.getNotifications(map)
                        .stream()
                        .map(notificationContractMapper::map)
                        .toList());
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(notificationContractMapper.map(notificationService.getById(id)));
    }

    @PostMapping("/notifications")
    public ResponseEntity<Notification> postNotification(@RequestBody @Valid Notification notification) {
        return ResponseEntity.ok(notificationContractMapper.map(notificationService.createNotification(notificationContractMapper.map(notification))));
    }

    @PutMapping("/notifications/{id}")
    public ResponseEntity<Notification> putNotification(@PathVariable("id") Integer id, @RequestBody @Valid Notification notification) {
        notification.setId(id);
        return ResponseEntity.ok(notificationContractMapper.map(notificationService.updateNotification(id, notificationContractMapper.map(notification))));
    }

    @PutMapping("/notifications/read/{id}")
    public ResponseEntity<Notification> postNotificationMarkAsRead(@PathVariable(name = "id") Integer id, @RequestBody @Valid Notification notification) {
        return ResponseEntity.ok(notificationContractMapper.map(notificationService.markAsRead(id)));
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Boolean> deleteNotification(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }

}
