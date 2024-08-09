package org.java.mentorship.notification.service;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.getNotifications();
    }

    public Notification createNotification(Notification notification) {

         return notificationRepository.createNotification(notification);
    }

}
