package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserFeignClient userFeignClient;

    @InjectMocks
    private UserController userController;

    @Test
    void getUsersShouldReturnDataFromFeign() {
        when(userFeignClient.getUsers())
                .thenReturn(Collections.singletonList(new User()));

        ResponseEntity<List<User>> response = userController.getUsers();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getUserByIdShouldReturnDataFromFeign() {
        when(userFeignClient.getUser(anyInt()))
                .thenReturn(new User());

        ResponseEntity<User> response = userController.getUserById(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }


    @Test
    void registerUserShouldReturnDataFromFeign() {
        when(userFeignClient.registerUser(any(RegistrationRequest.class)))
                .thenReturn(new User());

        ResponseEntity<User> response = userController.registerUser(RegistrationRequest.builder().build());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void verifyUserShouldReturnDataFromFeign() {
        when(userFeignClient.verifyUser(anyInt(), anyString()))
                .thenReturn(true);

        ResponseEntity<Boolean> response = userController.verifyUser(1, "AA-BB-CC");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
    }

}
