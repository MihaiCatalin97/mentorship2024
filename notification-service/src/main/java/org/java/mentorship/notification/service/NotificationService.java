package org.java.mentorship.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.contracts.email.client.EmailFeignClient;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.mapper.NotificationEmailMapper;
import org.java.mentorship.notification.repository.NotificationChannelRepository;
import org.java.mentorship.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationChannelRepository notificationChannelRepository;

    private final EmailFeignClient emailFeignClient;

    private final NotificationEmailMapper mapper;

    public List<NotificationEntity> getNotifications(Map<String, Object> params) {
        return notificationRepository.getNotifications(params).stream().peek(notificationEntity -> {
            Map<String, Object> filter = new HashMap<>();
            filter.put("notification_id", notificationEntity.getId());
            notificationEntity.setChannels(
                    notificationChannelRepository.getNotificationsChannels(filter)
            );
        }).toList();
    }

    @Transactional
    public NotificationEntity createNotification(NotificationEntity notification) {
        log.info("Creating notification {}", notification);
        NotificationEntity entity = notificationRepository.create(notification);
        notification.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        notification.getChannels()
                .forEach(channel -> notificationChannelRepository.createNotificationChannel(entity.getId(), channel));
        entity.setChannels(notification.getChannels());

        emailFeignClient.sendEmail(mapper.map(notification));

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
        filter.put("notification_id", notification.getId());
        notification.setChannels(
                notificationChannelRepository.getNotificationsChannels(filter)
        );
        return notification;
    }

    public boolean deleteNotification(Integer id) {
        return notificationRepository.deleteNotification(id);
    }
}
