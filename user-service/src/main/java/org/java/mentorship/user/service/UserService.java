package org.java.mentorship.user.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
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
    private final TokenService tokenService;

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
        user.setIsAdmin(false);
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

    public boolean verifyUserUsingToken(String token) {
        UserEntity user = mapper.findByVerificationToken(token).orElseThrow(UserNotFoundException::new);

        if (!tokenService.verifyVerificationToken(user, token)) return false;

        user.setVerifiedAt(OffsetDateTime.now(ZoneOffset.UTC));
        mapper.update(user);

        return true;
    }

    public Boolean resendVerificationToken(Integer userId) {
        UserEntity user = this.getUserById(userId).orElseThrow(UserNotFoundException::new);

        return tokenService.generateVerificationToken(user);
    }

    private Boolean changePassword(Integer userId, String newPassword) {
        UserEntity user = this.getUserById(userId).orElseThrow(UserNotFoundException::new);

        user.setHashedPassword(getMd5(newPassword));
        user.setLastChangedPassword(OffsetDateTime.now(ZoneOffset.UTC));
        user.setPasswordChangeToken(null);

        mapper.update(user);

        return true;
    }

    public Boolean changePasswordWithToken(String newPassword, String token) {
        UserEntity user = mapper.findByPasswordChangeToken(token).orElseThrow(UserNotFoundException::new);

        if (!tokenService.verifyPasswordChangeToken(user, token)) return false;

        return changePassword(user.getId(), newPassword);
    }

    public Boolean requestChangePasswordToken(Integer userId) {
        UserEntity user = this.getUserById(userId).orElseThrow(UserNotFoundException::new);

        return tokenService.generatePasswordChangeToken(user);
    }
}
