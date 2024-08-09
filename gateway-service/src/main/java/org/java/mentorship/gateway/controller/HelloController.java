package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final UserFeignClient userFeignClient;
    private final NotificationFeignClient notificationFeignClient;

    @GetMapping("/hello")
    ResponseEntity<List<User>> hello() {
        return ResponseEntity.ok(userFeignClient.getUsers());
    }

    @GetMapping("/hello")
    ResponseEntity<List<Notification>> notifications() {
        return ResponseEntity.ok(notificationFeignClient.getNotifications());
    }
}