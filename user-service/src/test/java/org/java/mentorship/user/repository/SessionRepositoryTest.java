package org.java.mentorship.user.repository;

import org.java.mentorship.contracts.user.dto.Session;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MybatisTest
@Sql(
        "classpath:schema.sql"
)
public class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void findShouldReturnAllSessions() {
        List<Session> sessions = sessionRepository.find(null);

        assertEquals(3, sessions.size());
    }

    @Test
    public void findByIdShouldReturnSessions() {
        List<Session> sessionsUser1 = sessionRepository.find(1);
        List<Session> sessionsUser2 = sessionRepository.find(2);

        assertEquals(2, sessionsUser1.size());
        assertEquals(1, sessionsUser2.size());
    }

    @Test
    public void insertShouldInsertSession() {
        sessionRepository.insert(Session.builder()
                        .expiresAt(OffsetDateTime.now())
                        .sessionKey("sess-key10")
                        .userId(1)
                .build());

        assertEquals(4, sessionRepository.find(null).size());
    }

    @Test
    public void getByKeyShouldReturnSession() {
        Optional<Session> session = sessionRepository.getByKey("sess-key1");

        assertTrue(session.isPresent());
        assertEquals("sess-key1", session.get().getSessionKey());
    }

}