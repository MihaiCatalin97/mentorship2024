package org.java.mentorship.user.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.java.mentorship.user.domain.SessionEntity;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.exception.domain.TooManySessionsException;
import org.java.mentorship.user.exception.domain.UserNotFoundException;
import org.java.mentorship.user.exception.domain.WrongPasswordException;
import org.java.mentorship.user.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserService userService;

    public static boolean isExpired(SessionEntity session) {
        return session.getExpiresAt().isBefore(OffsetDateTime.now(ZoneOffset.UTC));
    }

    public List<SessionEntity> find(Integer userId, Boolean isActive) {
        Stream<SessionEntity> stream = sessionRepository.find(userId).stream();

        if (Objects.isNull(isActive)) return stream.collect(Collectors.toList());
        if (isActive) return stream.filter(session -> !isExpired(session)).collect(Collectors.toList());
        return stream.filter(SessionService::isExpired).collect(Collectors.toList());
    }

    public Optional<SessionEntity> createSession(LoginRequest loginRequest) {
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
        if (find(user.getId(), true).size() >= 3) {
            throw new TooManySessionsException();
        }

        UUID sessionKey = UUID.randomUUID();
        SessionEntity session = new SessionEntity();
        session.setSessionKey(String.valueOf(sessionKey));
        session.setUserId(user.getId());
        session.setExpiresAt(OffsetDateTime.now(ZoneOffset.UTC).plusDays(30));

        // TODO: Call notification service with NEW_LOGIN message type
        sessionRepository.insert(session);

        return Optional.of(session);
    }

    public Optional<SessionEntity> getSession(String sessionKey) {
        Optional<SessionEntity> session = sessionRepository.getByKey(sessionKey);
        if (session.isEmpty()) return Optional.empty();
        if (isExpired(session.get())) return Optional.empty();
        return session;
    }

    public void deleteSession(String sessionKey) {
        Optional<SessionEntity> session = sessionRepository.getByKey(sessionKey);
        if (session.isEmpty()) return;
        sessionRepository.delete(session.get().getId());
    }
}
