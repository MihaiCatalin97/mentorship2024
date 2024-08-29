package org.java.mentorship.notification.mapper;

import org.java.mentorship.contracts.email.dto.EmailRequest;
import org.java.mentorship.contracts.email.dto.EmailType;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationEmailMapper {

    @Value("${verification.url.pattern}")
    private String verificationURLFormat;

    @Value("${recovery.url.pattern}")
    private String recoveryURLFormat;

    public EmailRequest map(final NotificationEntity notification) {
        return EmailRequest.builder()
                .type(mapType(notification))
                .payload(mapPayload(notification))
                .email(notification.getEmail())
                .build();
    }

    private EmailType mapType(final NotificationEntity notification) {
        EmailType emailType = null;

        switch (notification.getType()) {
            case VERIFICATION -> emailType = EmailType.VERIFICATION;
            case PASSWORD_CHANGE -> emailType = EmailType.RECOVERY;
        }

        return emailType;
    }

    private Map<String, Object> mapPayload(final NotificationEntity notification) {
        Map<String, Object> payload = new HashMap<>(notification.getPayload());

        payload.put("tokenUrl", mapURL(notification));
        payload.put("email", notification.getEmail());

        return payload;
    }

    private String mapURL(final NotificationEntity notification) {
        String token = notification.getPayload()
                .get("token")
                .toString();

        String url = null;

        switch (notification.getType()) {
            case VERIFICATION -> url = String.format(verificationURLFormat, token);
            case PASSWORD_CHANGE -> url = String.format(recoveryURLFormat, token);
        }

        return url;
    }
}
