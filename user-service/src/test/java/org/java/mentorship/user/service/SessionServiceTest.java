package org.java.mentorship.user.service;

import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.exception.domain.TooManySessionsException;
import org.java.mentorship.user.exception.domain.UserNotFoundException;
import org.java.mentorship.user.exception.domain.WrongPasswordException;
import org.java.mentorship.user.repository.mapper.SessionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    SessionMapper sessionMapper;

    @Mock
    UserService userService;

    @InjectMocks
    SessionService sessionService;

    @Captor
    ArgumentCaptor<Session> sessionCaptor;

    @Test
    void createSessionShouldCreateSession() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("admin@localhost")
                .password("SecretPassword").build();

        when(userService.getUserByEmail("admin@localhost")).thenReturn(
                Optional.ofNullable(UserEntity.builder()
                        .id(1)
                        .email("admin@localhost").build())
        );
        when(userService.verifyUserHash(1, "SecretPassword")).thenReturn(true);
        when(sessionMapper.getActiveSessionsByUser(1)).thenReturn(
                Arrays.asList(
                        mock(Session.class, RETURNS_DEEP_STUBS),
                        mock(Session.class, RETURNS_DEEP_STUBS)
                )
        );

        sessionService.createSession(loginRequest);

        verify(sessionMapper, times(1)).insertSession(sessionCaptor.capture());

        assertEquals(sessionCaptor.getValue().getUserId(), 1);
        assertTrue(sessionCaptor.getValue().getExpiresAt().isAfter(OffsetDateTime.now().plusDays(29)));
        assertTrue(sessionCaptor.getValue().getExpiresAt().isBefore(OffsetDateTime.now().plusDays(31)));

        verify(sessionMapper, times(1)).getSessionByKey(sessionCaptor.getValue().getSessionKey());
    }

    @Test
    void createSessionShouldThrowWhenTooManySessions() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("admin@localhost")
                .password("SecretPassword").build();

        when(userService.getUserByEmail("admin@localhost")).thenReturn(
                Optional.ofNullable(UserEntity.builder()
                        .id(1)
                        .email("admin@localost").build())
        );
        when(userService.verifyUserHash(1, "SecretPassword")).thenReturn(true);
        when(sessionMapper.getActiveSessionsByUser(1)).thenReturn(
                Arrays.asList(
                        mock(Session.class, RETURNS_DEEP_STUBS),
                        mock(Session.class, RETURNS_DEEP_STUBS),
                        mock(Session.class, RETURNS_DEEP_STUBS)
                )
        );

        assertThrows(
                TooManySessionsException.class,
                () -> sessionService.createSession(loginRequest));
    }

    @Test
    void createSessionShouldThrowWhenPasswordIsWrong() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("admin@localhost")
                .password("SecretPassword2").build();

        when(userService.getUserByEmail("admin@localhost")).thenReturn(
                Optional.ofNullable(UserEntity.builder()
                        .id(1)
                        .email("admin@localhost").build())
        );
        when(userService.verifyUserHash(1, "SecretPassword2")).thenReturn(false);

        assertThrows(
                WrongPasswordException.class,
                () -> sessionService.createSession(loginRequest));
    }

    @Test
    void createSessionShouldThrowWhenUserNotFound() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("admin@localhost")
                .password("SecretPassword2").build();

        when(userService.getUserByEmail("admin@localhost")).thenReturn(
                Optional.empty()
        );

        assertThrows(
                UserNotFoundException.class,
                () -> sessionService.createSession(loginRequest));
    }

    @Test
    void getSessionShouldCallRepository() {
        String sessionKey = UUID.randomUUID().toString();
        sessionService.getSession(sessionKey);
        verify(sessionMapper, times(1)).getSessionByKey(sessionKey);
    }

    @Test
    void getSessionsByUserShouldCallRepository() {
        sessionService.getSessionsByUser(1);
        verify(sessionMapper, times(1)).getSessionsByUser(1);
    }

    @Test
    void isExpiredShouldReturnTrueWhenSessionExpired() {
        Session session = Session.builder()
                .expiresAt(OffsetDateTime.now().minusSeconds(10))
                .build();

        assertTrue(SessionService.isExpired(session));
    }

    @Test
    void isExpiredShouldReturnFalseWhenSessionNotExpired() {
        Session session = Session.builder()
                .expiresAt(OffsetDateTime.now().plusDays(10))
                .build();

        assertFalse(SessionService.isExpired(session));
    }

    @Test
    void getActiveSessionShouldReturnActiveSession() {
        String sessionKey = UUID.randomUUID().toString();
        when(sessionMapper.getSessionByKey(sessionKey)).thenReturn(
                Optional.of(Session.builder().expiresAt(OffsetDateTime.now().plusDays(10)).build())
        );
        Optional<Session> session = sessionService.getActiveSession(sessionKey);
        assertTrue(session.isPresent());
    }

    @Test
    void getActiveSessionShouldNotReturnExpiredSession() {
        String sessionKey = UUID.randomUUID().toString();
        when(sessionMapper.getSessionByKey(sessionKey)).thenReturn(
                Optional.of(Session.builder().expiresAt(OffsetDateTime.now().minusDays(10)).build())
        );
        Optional<Session> session = sessionService.getActiveSession(sessionKey);
        assertTrue(session.isEmpty());
    }
}