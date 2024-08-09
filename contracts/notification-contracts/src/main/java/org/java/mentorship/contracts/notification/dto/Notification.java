package org.java.mentorship.contracts.notification.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

enum NotificationType {
    VERIFICATION, RECORD
}

enum NotificationChannel {
    EMAIL, WEB
}

@Data
public class Notification {

    private Integer id;
    private Integer user_id;
    private String email;
    private List<NotificationChannel> channels;
    private List<NotificationType> type;
    private Map<String, String> payload;
    private Boolean markedAsRead;
}
