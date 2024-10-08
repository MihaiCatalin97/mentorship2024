package org.java.mentorship.user.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${budget_tracker.tokens.password_change.expiration}")
    private static Integer PASSWORD_CHANGE_TOKEN_EXPIRATION = 10;

    @Value("${budget_tracker.tokens.verification.expiration}")
    private static Integer VERIFICATION_TOKEN_EXPIRATION = 10;

    @Value("${budget_tracker.tokens.verification.timeout}")
    private static Integer VERIFICATION_TOKEN_TIMEOUT = 10;

    @Value("${budget_tracker.tokens.password_change.timeout}")
    private static Integer PASSWORD_CHANGE_TOKEN_TIMEOUT = 10;

    private final UserRepository userRepository;
    private final NotificationFeignClient notificationFeignClient;

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private boolean isNotExpired(OffsetDateTime creationDate, Duration duration) {
        if (Objects.isNull(creationDate)) return true;
        return !OffsetDateTime.now().isAfter(creationDate.plus(duration));
    }

    private boolean inTimeout(OffsetDateTime creationDate, Duration duration) {
        if (Objects.isNull(creationDate)) return false;
        return OffsetDateTime.now().isBefore(creationDate.plus(duration));
    }

    public OffsetDateTime getLastSentPasswordChangeTokenDate(UserEntity user) {
        List<Notification> notifications = notificationFeignClient.getNotifications(
                user.getId(), user.getEmail(), null,
                NotificationType.PASSWORD_CHANGE, null
        );

        if (!notifications.isEmpty()) {
            return notifications.getLast().getCreatedAt();
        }

        return null;
    }

    public OffsetDateTime getLastSentVerificationNotificationDate(UserEntity user) {
        List<Notification> notifications = notificationFeignClient.getNotifications(
                user.getId(), user.getEmail(), null,
                NotificationType.VERIFICATION, null
        );

        if (!notifications.isEmpty()) {
            return notifications.getLast().getCreatedAt();
        }

        return null;
    }

    public boolean verifyPasswordChangeToken(UserEntity user, String token) {
        if (!Objects.equals(user.getPasswordChangeToken(), token)) return false;
        return isNotExpired(getLastSentPasswordChangeTokenDate(user), Duration.ofMinutes(PASSWORD_CHANGE_TOKEN_EXPIRATION));
    }

    public boolean verifyVerificationToken(UserEntity user, String token) {
        if (!Objects.equals(user.getVerificationToken(), token)) return false;
        return isNotExpired(getLastSentVerificationNotificationDate(user), Duration.ofMinutes(VERIFICATION_TOKEN_EXPIRATION));
    }

    public boolean generateVerificationToken(UserEntity user) {
        if (inTimeout(getLastSentVerificationNotificationDate(user), Duration.ofMinutes(VERIFICATION_TOKEN_TIMEOUT))) return false;

        user.setVerificationToken(this.generateToken());
        userRepository.update(user);

        notificationFeignClient.postNotification(new Notification(
                user.getId(), user.getEmail(),
                Collections.singletonList(NotificationChannel.EMAIL), NotificationType.VERIFICATION,
                Map.of(
                        "firstName", user.getFirstName(),
                        "lastName", user.getLastName(),
                        "token", user.getVerificationToken(),
                        "requestedAt", OffsetDateTime.now(ZoneOffset.UTC)
                )
        ));

        return true;
    }

    public boolean generatePasswordChangeToken(UserEntity user) {
        if (inTimeout(getLastSentPasswordChangeTokenDate(user), Duration.ofMinutes(PASSWORD_CHANGE_TOKEN_TIMEOUT))) return false;

        user.setPasswordChangeToken(this.generateToken());
        userRepository.update(user);

        notificationFeignClient.postNotification(new Notification(
                user.getId(), user.getEmail(),
                Collections.singletonList(NotificationChannel.EMAIL), NotificationType.PASSWORD_CHANGE,
                Map.of(
                        "firstName", user.getFirstName(),
                        "lastName", user.getLastName(),
                        "token", user.getPasswordChangeToken(),
                        "requestedAt", OffsetDateTime.now(ZoneOffset.UTC)
                )
        ));

        return true;
    }

}
