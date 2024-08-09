package org.java.mentorship.notification.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
@AllArgsConstructor
public class NotificationRepository {
    List<Notification> notifications = new ArrayList<>();

    public Notification createNotification(Notification notification) {
        notifications.add(notification);
        return notification;
    }

}
