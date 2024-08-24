package org.java.mentorship.user.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.exception.domain.AlreadyRegisteredException;
import org.java.mentorship.user.exception.domain.UserNotFoundException;
import org.java.mentorship.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.java.mentorship.user.crypt.MD5.getMd5;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository mapper;
    private final NotificationFeignClient notificationFeignClient;

    public List<UserEntity> getAllUsers() {
        return mapper.find();
    }

    public Optional<UserEntity> getUserById(Integer id) {
        return mapper.findById(id);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return mapper.findByEmail(email);
    }

    public Optional<UserEntity> registerUser(RegistrationRequest registrationRequest) {
        if (getUserByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new AlreadyRegisteredException();
        }

        final UserEntity user = new UserEntity();
        user.setEmail(registrationRequest.getEmail());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setHashedPassword(getMd5(registrationRequest.getPassword()));
        user.setLastChangedPassword(OffsetDateTime.now(ZoneOffset.UTC));
        user.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));

        mapper.insert(user);

        return Optional.of(user);
    }

    public boolean verifyUserHash(Integer id, String password) {
        Optional<UserEntity> user = getUserById(id);
        return user
                .map(userEntity -> userEntity.getHashedPassword().equals(getMd5(password)))
                .orElse(false);
    }

    public boolean verifyUserUsingToken(Integer id, String token) {
        UserEntity user = getUserById(id).orElseThrow(UserNotFoundException::new);
        if (!user.getVerificationToken().equals(token)) {
            return false;
        }

        if (user.getLastSentVerificationNotification() != null)
            if (OffsetDateTime.now(ZoneOffset.UTC).isAfter(user.getLastSentVerificationNotification().plusDays(1)))
                return false;

        user.setVerifiedAt(OffsetDateTime.now(ZoneOffset.UTC));
        mapper.update(user);

        return true;
    }

    public Boolean resendVerificationToken(Integer userId) {
        UserEntity user = this.getUserById(userId).orElseThrow(UserNotFoundException::new);

        if (user.getLastSentVerificationNotification() != null)
            if (!OffsetDateTime.now(ZoneOffset.UTC).isAfter(user.getLastSentVerificationNotification().plusMinutes(10)))
                return false;

        user.setVerificationToken(UUID.randomUUID().toString());
        user.setLastSentVerificationNotification(OffsetDateTime.now(ZoneOffset.UTC));

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

        mapper.update(user);

        return true;
    }

    public Boolean changePassword(Integer userId, String newPassword) {
        UserEntity user = this.getUserById(userId).orElseThrow(UserNotFoundException::new);

        user.setHashedPassword(getMd5(newPassword));
        user.setLastChangedPassword(OffsetDateTime.now(ZoneOffset.UTC));
        user.setPasswordChangeToken(null);

        mapper.update(user);

        return true;
    }

    public Boolean changePasswordWithToken(Integer userId, String newPassword, String token) {
        UserEntity user = this.getUserById(userId).orElseThrow(UserNotFoundException::new);

        if (user.getLastSentPasswordChangeToken() != null)
            if (OffsetDateTime.now(ZoneOffset.UTC).isAfter(user.getLastSentPasswordChangeToken().plusDays(1)))
                return false;

        if (!Objects.equals(user.getPasswordChangeToken(), token)) return false;

        return changePassword(userId, newPassword);
    }

    public Boolean requestChangePasswordToken(Integer userId) {
        UserEntity user = this.getUserById(userId).orElseThrow(UserNotFoundException::new);

        if (user.getLastSentPasswordChangeToken() != null)
            if (!OffsetDateTime.now(ZoneOffset.UTC).isAfter(user.getLastSentPasswordChangeToken().plusMinutes(10)))
                return false;

        user.setPasswordChangeToken(UUID.randomUUID().toString());
        user.setLastSentPasswordChangeToken(OffsetDateTime.now(ZoneOffset.UTC));

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

        mapper.update(user);

        return true;
    }
}
