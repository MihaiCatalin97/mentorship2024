package org.java.mentorship.notification.mapper;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.springframework.stereotype.Component;



@Component
public class NotificationToContract {
    public static Notification convert(NotificationEntity notificationEntity) {
        Notification notification = new Notification();


        notification.setId(notificationEntity.getId());
        notification.setUserId(notificationEntity.getUserId());
        notification.setEmail(notificationEntity.getEmail());
        notification.setMarkedAsRead(notificationEntity.getMarkedAsRead());
        notification.setType(NotificationType.valueOf(notificationEntity.getType().toString()));
        notification.setPayload(notificationEntity.getPayload());
        notification.setCreatedAt(notificationEntity.getCreatedAt());
        return notification;
    }
}
