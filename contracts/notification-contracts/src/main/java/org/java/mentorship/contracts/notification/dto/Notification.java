package org.java.mentorship.contracts.notification.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Integer id;
    private Boolean markedAsRead;
    private OffsetDateTime createdAt;

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
}
