package org.java.mentorship.user.controller;

import org.java.mentorship.contracts.user.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        User user = new User();
        user.setEmail("test");

        return ResponseEntity.ok(Collections.singletonList(user));
    }
}
