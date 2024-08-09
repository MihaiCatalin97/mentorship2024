package org.java.mentorship.notification.service;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getWebNotifications() {
        return notificationRepository.getNotifications();
    }

    public Notification createNotification(Notification notification) {
        notification.setCreatedAt(Instant.now().getEpochSecond());
        return notificationRepository.createNotification(notification);
    }

    public Notification markAsRead(Integer id) {
        Notification notification = notificationRepository.getNotificationById(id);
        notification.setMarkedAsRead(true);
        notificationRepository.updateNotification(notification.getId(), notification);
        return notification;
    }


    public Notification getById(Integer id) {
        return notificationRepository.getNotificationById(id);
    }

    public List<Notification> getWebNotificationsByUser(Integer userId) {
        return notificationRepository.getWebNotificationsByUser(userId);
    }
}
