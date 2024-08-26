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

    public boolean verifyPasswordChangeToken(UserEntity user, String token) {
        if (!Objects.equals(user.getPasswordChangeToken(), token)) return false;
        return isNotExpired(user.getLastSentPasswordChangeToken(), Duration.ofMinutes(PASSWORD_CHANGE_TOKEN_EXPIRATION));
    }

    public boolean verifyVerificationToken(UserEntity user, String token) {
        if (!Objects.equals(user.getVerificationToken(), token)) return false;
        return isNotExpired(user.getLastSentVerificationNotification(), Duration.ofMinutes(VERIFICATION_TOKEN_EXPIRATION));
    }

    public boolean generateVerificationToken(UserEntity user) {
        if (inTimeout(user.getLastSentVerificationNotification(), Duration.ofMinutes(VERIFICATION_TOKEN_TIMEOUT))) return false;

        user.setVerificationToken(this.generateToken());
        user.setLastSentVerificationNotification(OffsetDateTime.now());

        userRepository.update(user);

        notificationFeignClient.postNotification(new Notification(
                user.getId(), user.getEmail(),
                Collections.singletonList(NotificationChannel.EMAIL), NotificationType.VERIFICATION,
                Map.of(
                        "firstName", user.getFirstName(),
                        "lastName", user.getLastName(),
                        "verificationToken", user.getVerificationToken(),
                        "requestedAt", OffsetDateTime.now(ZoneOffset.UTC)
                )
        ));

        return true;
    }

    public boolean generatePasswordChangeToken(UserEntity user) {
        if (inTimeout(user.getLastSentPasswordChangeToken(), Duration.ofMinutes(PASSWORD_CHANGE_TOKEN_TIMEOUT))) return false;

        user.setPasswordChangeToken(this.generateToken());
        user.setLastSentPasswordChangeToken(OffsetDateTime.now());

        userRepository.update(user);

        notificationFeignClient.postNotification(new Notification(
                user.getId(), user.getEmail(),
                Collections.singletonList(NotificationChannel.EMAIL), NotificationType.PASSWORD_CHANGE,
                Map.of(
                        "firstName", user.getFirstName(),
                        "lastName", user.getLastName(),
                        "passwordChangeToken", user.getPasswordChangeToken(),
                        "requestedAt", OffsetDateTime.now(ZoneOffset.UTC)
                )
        ));

        return true;
    }

}
