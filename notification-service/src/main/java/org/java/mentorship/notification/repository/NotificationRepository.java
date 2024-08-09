package org.java.mentorship.notification.repository;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class NotificationRepository {
    List<Notification> notifications = new ArrayList<>();

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Notification getNotificationById(Integer id) {
        return notifications.stream().filter(notification -> notification.getId().equals(id)).findFirst().orElse(null);
    }

    public Notification updateNotification(Integer id, Notification notification) {
        // search and update
        notification.setMarkedAsRead(true);
        return notification;
    }

    public Notification createNotification(Notification notification) {
        notifications.add(notification);
        return notification;
    }

    public List<Notification> getWebNotificationsByUser(Integer userId) {
        return notifications.stream().filter(notification -> notification.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
