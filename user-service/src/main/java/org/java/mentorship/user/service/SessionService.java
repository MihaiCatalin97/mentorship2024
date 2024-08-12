package org.java.mentorship.user.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.repository.mapper.SessionMapper;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionMapper sessionMapper;
    private final UserService userService;

    public Optional<Session> createSession(LoginRequest loginRequest) {
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
        if (getActiveSessionsByUser(user.getId()).size() >= 3) {
            return Optional.empty();
        }

        UUID sessionKey = UUID.randomUUID();
        Session session = new Session();
        session.setSessionKey(String.valueOf(sessionKey));
        session.setUserId(user.getId());
        session.setExpiresAt(OffsetDateTime.now().plusDays(30));

        sessionMapper.insertSession(session);

        return Optional.of(session);
    }

    public List<Session> getActiveSessionsByUser(Integer id) {
        return sessionMapper.getActiveSessionsByUser(id);
    }

    public Optional<Session> getSession(String sessionKey) {
        return sessionMapper.getSessionByKey(sessionKey);
    }

    public List<Session> getSessionsByUser(Integer id) {
        return sessionMapper.getSessionsByUser(id);
    }

    public static boolean isExpired(Session session) {
        return session.getExpiresAt().isBefore(OffsetDateTime.now());
    }

    public Optional<Session> getActiveSession(String key) {
        return getSession(key).map(session -> isExpired(session) ? null : session);
    }
}
