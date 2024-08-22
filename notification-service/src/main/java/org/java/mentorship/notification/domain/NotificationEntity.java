package org.java.mentorship.notification.domain;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.java.mentorship.notification.domain.enums.NotificationChannel;
import org.java.mentorship.notification.domain.enums.NotificationType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {

    private Integer id;
    private Integer userId;
    private String email;
    private List<NotificationChannel> channels;
    private NotificationType type;
    private Map<String, Object> payload;
    private Boolean markedAsRead;
    private OffsetDateTime createdAt;
}
