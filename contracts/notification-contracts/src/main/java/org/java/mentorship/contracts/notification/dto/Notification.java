package org.java.mentorship.contracts.notification.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class Notification {

    private Integer id;
    private Integer userId;
    private String email;
    private List<NotificationChannel> channels;
    private List<NotificationType> type;
    private Map<String, Object> payload;
    private Boolean markedAsRead;
    private Long createdAt;
}
