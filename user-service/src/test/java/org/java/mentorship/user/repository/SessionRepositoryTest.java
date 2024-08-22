package org.java.mentorship.user.repository;

import org.java.mentorship.user.domain.SessionEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MybatisTest
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void findShouldReturnAllSessions() {
        List<SessionEntity> sessions = sessionRepository.find(null);

        assertEquals(3, sessions.size());
    }

    @Test
    void findByIdShouldReturnSessions() {
        List<SessionEntity> sessionsUser1 = sessionRepository.find(1);
        List<SessionEntity> sessionsUser2 = sessionRepository.find(2);

        assertEquals(2, sessionsUser1.size());
        assertEquals(1, sessionsUser2.size());
    }

    @Test
    void insertShouldInsertSession() {
        sessionRepository.insert(SessionEntity.builder()
                .expiresAt(OffsetDateTime.now())
                .sessionKey("sess-key10")
                .userId(1)
                .build());

        assertEquals(4, sessionRepository.find(null).size());
    }

    @Test
    void getByKeyShouldReturnSession() {
        Optional<SessionEntity> session = sessionRepository.getByKey("sess-key1");

        assertTrue(session.isPresent());
        assertEquals("sess-key1", session.get().getSessionKey());
    }

}