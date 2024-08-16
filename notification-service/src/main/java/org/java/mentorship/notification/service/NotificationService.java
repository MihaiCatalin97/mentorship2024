package org.java.mentorship.notification.service;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.mapper.NotificationToContract;
import org.java.mentorship.notification.repository.NotificationChannelRepository;
import org.java.mentorship.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.java.mentorship.notification.mapper.NotificationToContract.convert;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationChannelRepository notificationChannelRepository;

    public List<Notification> getWebNotifications(Map<String, Object> params) {
        List<NotificationEntity> notificationEntities = notificationRepository.getNotifications(params);
//        return notificationEntities.stream()
//                .map(it -> {
//                    it.setChannels(notificationChannelRepository.getNotificationsById(it.getId()));
//                    return convert(it);
//                })
//                .collect(Collectors.toList());
       return notificationEntities.stream().map(NotificationToContract::convert).collect(Collectors.toList());
    }

    public Notification createNotification(NotificationEntity notification) {
        notification.setChannels(notificationChannelRepository.getNotificationsById(notification.getId()));
        return convert(notificationRepository.create(notification));

    }

    public Notification updateNotification(Integer id, NotificationEntity notification) {
        notification.setChannels(notificationChannelRepository.getNotificationsById(id));
        return convert(notificationRepository.update(id, notification));

    }

    public Notification markAsRead(Integer id) {
        NotificationEntity notification = notificationRepository.getNotificationById(id);
        notification.setMarkedAsRead(true);
        notificationRepository.update(notification.getId(), notification);
        return convert(notification);
    }


    public Notification getById(Integer id) {
         return convert(notificationRepository.getNotificationById(id));
    }

    public boolean deleteNotification(Integer id) {
        return notificationRepository.deleteNotification(id);
    }


    public List<Notification> getWebNotificationsByUser(Integer userId) {
         return notificationRepository.getWebNotificationsByUser(userId)
                 .stream()
                 .map(it -> convert(it))
                 .collect(Collectors.toList());
    }
}
