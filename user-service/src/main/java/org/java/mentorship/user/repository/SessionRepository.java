package org.java.mentorship.user.repository;

import org.java.mentorship.contracts.user.dto.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SessionRepository {

    private final List<Session> sessions = new ArrayList<>();

    public List<Session> getAllSessions() {
        return sessions;
    }

    public Session createSession(Session session) {
        sessions.add(session);
        return session;
    }

    public Optional<Session> getSessionByKey(String key) {
        return sessions.stream()
                .filter(session -> session.getSessionKey().equals(key)).findFirst();
    }

    public List<Session> getSessionsByUser(Integer id) {
        return sessions.stream()
                .filter(session -> session.getUserId().equals(id))
                .collect(Collectors.toList());
    }
}
