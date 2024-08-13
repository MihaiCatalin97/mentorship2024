package org.java.mentorship.user.controller;

import org.java.mentorship.contracts.user.dto.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    private final UserController userController = new UserController();

    @Test
    void getUsersShouldReturnUsers() {
        // When
        List<User> result = userController.getUsers()
                .getBody();

        // Then
        assertEquals(1, result.size());
    }
}