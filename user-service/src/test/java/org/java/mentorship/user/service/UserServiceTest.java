package org.java.mentorship.user.service;

import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.user.crypt.MD5;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.repository.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Captor
    private ArgumentCaptor<UserEntity> userArgumentCaptor;

    @Test
    void registerUserShouldSaveUser() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("First Name")
                .lastName("Last Name")
                .password("SecretPassword")
                .email("admin@localhost")
                .build();

        userService.registerUser(registrationRequest);

        verify(userMapper).insertUser(userArgumentCaptor.capture());
        verify(userMapper, times(2)).findByEmail("admin@localhost");

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

        verify(userMapper).insertUser(userArgumentCaptor.capture());
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

        when(userMapper.findByEmail(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().build()));

        Optional<UserEntity> userEntityOptional = userService.registerUser(registrationRequest);

        verify(userMapper, never()).insertUser(any());
        verify(userMapper, times(1)).findByEmail("admin@localhost");

        assertFalse(userEntityOptional.isPresent());
    }

    @Test
    void getAllUsersShouldCallRepository() {
        userService.getAllUsers();
        verify(userMapper, times(1)).findAll();
    }

    @Test
    void getUserByIdShouldCallRepository() {
        userService.getUserById(1);
        verify(userMapper, times(1)).findById(1);
    }

    @Test
    void getUserByEmailShouldCallRepository() {
        userService.getUserByEmail("admin@localhost");
        verify(userMapper, times(1)).findByEmail("admin@localhost");
    }

    @Test
    void verifyUserHashShouldReturnTrueWhenPasswordsMatch() {
        when(userMapper.findById(1)).thenReturn(Optional.ofNullable(
                UserEntity.builder().hashedPassword(MD5.getMd5("SecretPassword")).build()
        ));

        boolean result = userService.verifyUserHash(1, "SecretPassword");

        assertTrue(result);
    }

    @Test
    void verifyUserHashShouldReturnFalseWhenPasswordsDontMatch() {
        when(userMapper.findById(1)).thenReturn(Optional.ofNullable(
                UserEntity.builder().hashedPassword(MD5.getMd5("SecretPassword2")).build()
        ));

        boolean result = userService.verifyUserHash(1, "SecretPassword1");

        assertFalse(result);
    }

    @Test
    void verifyUserUsingTokenShouldEditUser() {
        String verificationToken = UUID.randomUUID().toString();

        when(userMapper.findById(1)).thenReturn(Optional.ofNullable(
                UserEntity.builder().verificationToken(verificationToken).build()
        ));
        when(userMapper.setUserVerifiedStatus(1, true)).thenReturn(true);

        boolean result = userService.verifyUserUsingToken(1, verificationToken);

        assertTrue(result);
        verify(userMapper, times(1)).findById(1);
        verify(userMapper, times(1)).setUserVerifiedStatus(1, true);
    }

    @Test
    void verifyUserUsingTokenShouldNotEditUser() {
        String verificationToken = UUID.randomUUID().toString();

        when(userMapper.findById(1)).thenReturn(Optional.ofNullable(
                UserEntity.builder().verificationToken(UUID.randomUUID().toString()).build()
        ));

        boolean result = userService.verifyUserUsingToken(1, verificationToken);

        assertFalse(result);
        verify(userMapper, times(1)).findById(1);
        verify(userMapper, times(0)).setUserVerifiedStatus(anyInt(), anyBoolean());
    }
}
