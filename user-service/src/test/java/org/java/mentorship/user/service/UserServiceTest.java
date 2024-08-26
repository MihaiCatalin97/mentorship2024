package org.java.mentorship.user.service;

import org.java.mentorship.contracts.notification.client.NotificationFeignClient;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.user.crypt.MD5;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.exception.domain.AlreadyRegisteredException;
import org.java.mentorship.user.exception.domain.UserNotFoundException;
import org.java.mentorship.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationFeignClient notificationFeignClient;

    @Captor
    private ArgumentCaptor<UserEntity> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<Notification> notificationArgumentCaptor;

    @Test
    void registerUserShouldSaveUser() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("First Name")
                .lastName("Last Name")
                .password("SecretPassword")
                .email("admin@localhost")
                .build();

        userService.registerUser(registrationRequest);

        verify(userRepository).insert(userArgumentCaptor.capture());

        UserEntity savedEntity = userArgumentCaptor.getValue();
        assertEquals(registrationRequest.getFirstName(), savedEntity.getFirstName());
        assertEquals(registrationRequest.getLastName(), savedEntity.getLastName());
        assertEquals(registrationRequest.getEmail(), savedEntity.getEmail());
        assertNotEquals(registrationRequest.getPassword(), savedEntity.getHashedPassword());
    }

    @Test
    void registerUserShouldSaveHashedPassword() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("First Name")
                .lastName("Last Name")
                .password("SecretPassword")
                .email("admin@localhost")
                .build();

        userService.registerUser(registrationRequest);

        verify(userRepository).insert(userArgumentCaptor.capture());
        UserEntity savedEntity = userArgumentCaptor.getValue();

        assertEquals(MD5.getMd5(registrationRequest.getPassword()), savedEntity.getHashedPassword());
    }

    @Test
    void registerUserShouldReturnEmptyWhenEmailAlreadyRegistered() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("First Name")
                .lastName("Last Name")
                .password("SecretPassword")
                .email("admin@localhost")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().build()));

        assertThrows(AlreadyRegisteredException.class, () -> userService.registerUser(registrationRequest));

        verify(userRepository, never()).insert(any());
        verify(userRepository, times(1)).findByEmail("admin@localhost");
    }

    @Test
    void getAllUsersShouldCallRepository() {
        userService.getAllUsers();
        verify(userRepository, times(1)).find();
    }

    @Test
    void getUserByIdShouldCallRepository() {
        userService.getUserById(1);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getUserByEmailShouldCallRepository() {
        userService.getUserByEmail("admin@localhost");
        verify(userRepository, times(1)).findByEmail("admin@localhost");
    }

    @Test
    void verifyUserHashShouldReturnTrueWhenPasswordsMatch() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(
                UserEntity.builder().hashedPassword(MD5.getMd5("SecretPassword")).build()
        ));

        boolean result = userService.verifyUserHash(1, "SecretPassword");

        assertTrue(result);
    }

    @Test
    void verifyUserHashShouldReturnFalseWhenPasswordsDontMatch() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(
                UserEntity.builder().hashedPassword(MD5.getMd5("SecretPassword2")).build()
        ));

        boolean result = userService.verifyUserHash(1, "SecretPassword1");

        assertFalse(result);
    }

    @Test
    void verifyUserUsingTokenShouldEditUserWhenTokenMatches() {
        String verificationToken = UUID.randomUUID().toString();

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(
                UserEntity.builder().verificationToken(verificationToken).build()
        ));

        boolean result = userService.verifyUserUsingToken(1, verificationToken);

        assertTrue(result);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).update(any());
    }

    @Test
    void verifyUserUsingTokenShouldNotEditUserWhenTokenWrong() {
        String verificationToken = UUID.randomUUID().toString();

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(
                UserEntity.builder().verificationToken(UUID.randomUUID().toString()).build()
        ));

        boolean result = userService.verifyUserUsingToken(1, verificationToken);

        assertFalse(result);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(0)).update(any());
    }

    @Test
    void verifyUserUsingTokenShouldThrowWhenUserDoesntExist() {
        String verificationToken = UUID.randomUUID().toString();

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.verifyUserUsingToken(1, verificationToken));
    }

    @Test
    void verifyUserUsingTokenShouldThrowWhenTokenExpired() {
        String verificationToken = UUID.randomUUID().toString();

        when(userRepository.findById(1)).thenReturn(
                Optional.of(UserEntity.builder()
                                .verificationToken(verificationToken)
                                .lastSentVerificationNotification(OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(11))
                        .build())
        );

        boolean result = userService.verifyUserUsingToken(1, verificationToken);

        assertFalse(result);
    }

    @Test
    void resendVerificationTokenShouldSetVerificationToken() {
        when(userRepository.findById(1)).thenReturn(Optional.of(
                UserEntity.builder()
                        .firstName("first")
                        .lastName("last")
                        .email("email@email.com")
                        .id(1)
                        .build()
        ));

        Boolean result = userService.resendVerificationToken(1);

        verify(notificationFeignClient).postNotification(notificationArgumentCaptor.capture());
        verify(userRepository).update(userArgumentCaptor.capture());
        Notification notification = notificationArgumentCaptor.getValue();
        UserEntity user = userArgumentCaptor.getValue();

        assertTrue(result);
        assertEquals(1, notification.getUserId());
        assertNotNull(user.getVerificationToken());
        assertNotNull(user.getLastSentVerificationNotification());
    }

    @Test
    void resendVerificationTokenShouldReturnFalseWhenUserAlreadyRequestedAToken() {
        when(userRepository.findById(1)).thenReturn(Optional.of(
                UserEntity.builder()
                        .firstName("first")
                        .lastName("last")
                        .email("email@email.com")
                        .lastSentVerificationNotification(OffsetDateTime.now(ZoneOffset.UTC))
                        .id(1)
                        .build()
        ));

        Boolean result = userService.resendVerificationToken(1);

        assertFalse(result);
    }

    @Test
    void changePasswordShouldChangePassword() {
        when(userRepository.findById(1)).thenReturn(Optional.of(
                UserEntity.builder().build()
        ));

        boolean result = userService.changePassword(1, "SecretPassword");

        assertTrue(result);
        verify(userRepository, times(1)).update(userArgumentCaptor.capture());
        UserEntity savedEntity = userArgumentCaptor.getValue();
        assertEquals(MD5.getMd5("SecretPassword"), savedEntity.getHashedPassword());
    }

    @Test
    void changePasswordWithTokenShouldChangePassword() {
        when(userRepository.findById(1)).thenReturn(Optional.of(
                UserEntity.builder()
                        .passwordChangeToken("aaa-bbb")
                        .build()
        ));

        boolean result = userService.changePasswordWithToken(1, "SecretPassword", "aaa-bbb");

        verify(userRepository, times(1)).update(userArgumentCaptor.capture());

        UserEntity savedEntity = userArgumentCaptor.getValue();

        assertTrue(result);
        assertEquals(MD5.getMd5("SecretPassword"), savedEntity.getHashedPassword());
    }

    @Test
    void changePasswordWithTokenShouldNotChangeWhenTokenInvalid() {
        when(userRepository.findById(1)).thenReturn(Optional.of(
                UserEntity.builder()
                        .passwordChangeToken("aaa-bbb")
                        .lastSentPasswordChangeToken(OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(11))
                        .build()
        ));

        boolean result = userService.changePasswordWithToken(1, "SecretPassword", "aaa-bbb");

        verify(userRepository, times(0)).update(any());

        assertFalse(result);
    }

    @Test
    void requestChangePasswordTokenShouldCallNotification() {
        when(userRepository.findById(1)).thenReturn(Optional.of(
                UserEntity.builder()
                        .firstName("First name")
                        .lastName("Last name")
                        .build()
        ));

        boolean result = userService.requestChangePasswordToken(1);

        verify(notificationFeignClient).postNotification(notificationArgumentCaptor.capture());
        verify(userRepository).update(userArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();
        UserEntity savedEntity = userArgumentCaptor.getValue();

        assertTrue(result);
        assertNotNull(notification.getPayload().get("passwordChangeToken"));
        assertNotNull(savedEntity.getLastSentPasswordChangeToken());
        assertNotNull(savedEntity.getPasswordChangeToken());
    }
}
