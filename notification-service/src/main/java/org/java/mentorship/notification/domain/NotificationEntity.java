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

    @NotNull(message = "ID cannot be null")
    private Integer id;

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Notification channels cannot be null")
    private List<NotificationChannel> channels;

    @NotNull(message = "Notification types cannot be null")
    private NotificationType type;

    @NotNull(message = "Payload cannot be null")
    @Size(min = 1, message = "Payload cannot be empty")
    private Map<String, Object> payload;

    @NotNull(message = "Marked as read status cannot be null")
    private Boolean markedAsRead;

    @NotNull(message = "Creation date cannot be null")
    private OffsetDateTime createdAt;
}
