package org.java.mentorship.contracts.notification.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
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

    public Notification(Integer userId, String email, List<NotificationChannel> channels, NotificationType type, Map<String, Object> payload) {
        this.userId = userId;
        this.email = email;
        this.channels = channels;
        this.type = type;
        this.payload = payload;
        this.markedAsRead = false;
        this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
