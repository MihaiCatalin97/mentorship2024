package org.java.mentorship.user.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.exception.domain.TooManySessionsException;
import org.java.mentorship.user.exception.domain.UserNotFoundException;
import org.java.mentorship.user.exception.domain.WrongPasswordException;
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

    public static boolean isExpired(Session session) {
        return session.getExpiresAt().isBefore(OffsetDateTime.now());
    }

    public Optional<Session> createSession(LoginRequest loginRequest) {
        // TODO: Return proper exceptions

        Optional<UserEntity> userEntity = userService.getUserByEmail(loginRequest.getEmail());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        UserEntity user = userEntity.get();

        // Wrong Password
        if (!userService.verifyUserHash(user.getId(), loginRequest.getPassword())) {
            throw new WrongPasswordException();
        }

        // Too many sessions for a user
        if (getActiveSessionsByUser(user.getId()).size() >= 3) {
            throw new TooManySessionsException();
        }

        UUID sessionKey = UUID.randomUUID();
        Session session = new Session();
        session.setSessionKey(String.valueOf(sessionKey));
        session.setUserId(user.getId());
        session.setExpiresAt(OffsetDateTime.now().plusDays(30));

        sessionMapper.insertSession(session);

        return sessionMapper.getSessionByKey(session.getSessionKey());
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

    public Optional<Session> getActiveSession(String key) {
        return getSession(key).map(session -> isExpired(session) ? null : session);
    }
}
