package org.java.mentorship.user.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.UserLoginRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserService userService;

    public Optional<Session> createSession(UserLoginRequest loginRequest) {
        // TODO: Return proper exceptions

        Optional<UserEntity> userEntity = userService.getUserByEmail(loginRequest.getEmail());
        if (userEntity.isEmpty()) {
            return Optional.empty();
        }

        UserEntity user = userEntity.get();

        // Wrong Password
        if (!userService.verifyUserHash(user.getId(), loginRequest.getPassword())) {
            return Optional.empty();
        }

        // Too many sessions for a user
        if (getSessionsByUser(user.getId()).size() >= 3) {
            return Optional.empty();
        }

        UUID sessionKey = UUID.randomUUID();
        Session session = new Session();
        session.setSessionKey(String.valueOf(sessionKey));
        session.setUserId(user.getId());
        session.setExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS).getEpochSecond());

        return Optional.of(sessionRepository.createSession(session));
    }

    public Optional<Session> getSession(String sessionKey) {
        return sessionRepository.getSessionByKey(sessionKey);
    }

    public List<Session> getSessionsByUser(Integer id) {
        return sessionRepository.getSessionsByUser(id);
    }
}
