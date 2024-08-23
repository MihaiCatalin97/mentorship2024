package org.java.mentorship.user.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.exception.domain.AlreadyRegisteredException;
import org.java.mentorship.user.exception.domain.UserNotFoundException;
import org.java.mentorship.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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
        user.setVerified(false);
        user.setHashedPassword(getMd5(registrationRequest.getPassword()));
        user.setVerificationToken(UUID.randomUUID().toString());

        mapper.insert(user);

        Notification verificationNotification = new Notification();

        verificationNotification.setEmail(user.getEmail());
        verificationNotification.setUserId(user.getId());
        verificationNotification.setChannels(Collections.singletonList(NotificationChannel.EMAIL));
        verificationNotification.setType(NotificationType.VERIFICATION);
        verificationNotification.setPayload(Map.of(
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "verificationToken", user.getVerificationToken(),
                "requestedAt", OffsetDateTime.now()
        ));

        notificationFeignClient.postNotification(verificationNotification);

        return Optional.of(user);
    }

    public boolean verifyUserHash(Integer id, String password) {
        Optional<UserEntity> user = getUserById(id);
        return user
                .map(userEntity -> userEntity.getHashedPassword().equals(getMd5(password)))
                .orElse(false);
    }

    public boolean verifyUserUsingToken(Integer id, String token) {
        Optional<UserEntity> user = getUserById(id);
        if (user.isEmpty()) throw new UserNotFoundException();
        if (!user.get().getVerificationToken().equals(token)) {
            return false;
        }

        return mapper.setUserVerifiedStatus(id, true);
    }
}
