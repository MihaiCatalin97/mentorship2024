package org.java.mentorship.notification.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificationServiceError {
    private List<String> message;
}

