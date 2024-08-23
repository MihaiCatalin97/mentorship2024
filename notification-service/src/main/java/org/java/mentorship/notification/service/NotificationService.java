package org.java.mentorship.notification.service;

import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.repository.NotificationChannelRepository;
import org.java.mentorship.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationChannelRepository notificationChannelRepository;

    public List<NotificationEntity> getNotifications(Map<String, Object> params) {
        return notificationRepository.getNotifications(params);
    }

    public NotificationEntity createNotification(NotificationEntity notification) {
        NotificationEntity entity = notificationRepository.create(notification);
        notification.setCreatedAt(OffsetDateTime.now());
        notification.getChannels().forEach(channel -> {
            notificationChannelRepository.createNotificationChannel(entity.getId(), channel);
        });
        entity.setChannels(notification.getChannels());
        return entity;
    }

    public NotificationEntity updateNotification(Integer id, NotificationEntity notification) {
        notification.setChannels(notificationChannelRepository.getNotificationsById(id));
        return notificationRepository.update(id, notification);

    }

    public NotificationEntity markAsRead(Integer id) {
        NotificationEntity notification = notificationRepository.getNotificationById(id);
        notification.setMarkedAsRead(true);
        notificationRepository.update(notification.getId(), notification);
        return notification;
    }

    public NotificationEntity getById(Integer id) {
        NotificationEntity notification = notificationRepository.getNotificationById(id);
        Map<String, Object> filter = new HashMap<>();
        filter.put("id", notification.getId());
        notification.setChannels(
                notificationChannelRepository.getNotificationsChannels(filter)
        );
        return notification;
    }

    public boolean deleteNotification(Integer id) {
        return notificationRepository.deleteNotification(id);
    }


}
