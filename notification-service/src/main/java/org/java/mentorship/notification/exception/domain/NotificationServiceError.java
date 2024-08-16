package org.java.mentorship.notification.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationServiceError {
    private String message;
}

