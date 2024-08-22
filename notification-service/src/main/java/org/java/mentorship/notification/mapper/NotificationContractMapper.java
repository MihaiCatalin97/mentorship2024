package org.java.mentorship.notification.mapper;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NotificationContractMapper {

    public Notification map(NotificationEntity notificationEntity) {
        Notification notification = new Notification();

        notification.setId(notificationEntity.getId());
        notification.setUserId(notificationEntity.getUserId());
        notification.setEmail(notificationEntity.getEmail());
        notification.setMarkedAsRead(notificationEntity.getMarkedAsRead());
        notification.setType(NotificationType.valueOf(notificationEntity.getType().toString()));
        if (notificationEntity.getChannels() != null) {
            notification.setChannels(notificationEntity.getChannels().stream().map(
                    channel -> NotificationChannel.valueOf(channel.toString())
            ).collect(Collectors.toList()));
        }
        notification.setPayload(notificationEntity.getPayload());
        notification.setCreatedAt(notificationEntity.getCreatedAt());

        return notification;
    }

    public NotificationEntity map(Notification notification) {
        NotificationEntity notificationEntity = new NotificationEntity();

        notificationEntity.setId(notification.getId());
        notificationEntity.setUserId(notification.getUserId());
        notificationEntity.setEmail(notification.getEmail());
        notificationEntity.setMarkedAsRead(notification.getMarkedAsRead());
        notificationEntity.setType(org.java.mentorship.notification.domain.enums.NotificationType.valueOf(notification.getType().toString()));
        if (notification.getChannels() != null) {
            notificationEntity.setChannels(notification.getChannels().stream().map(
                    channel -> org.java.mentorship.notification.domain.enums.NotificationChannel.valueOf(channel.toString())
            ).collect(Collectors.toList()));
        }
        notificationEntity.setPayload(notification.getPayload());
        notificationEntity.setCreatedAt(notification.getCreatedAt());

        return notificationEntity;
    }
}
