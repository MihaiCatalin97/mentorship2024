package org.java.mentorship.user.controller;

import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(
                userService
                        .getAllUsers()
                        .stream()
                        .map(UserEntity::ToContract)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name="id") Integer id) {
        Optional<UserEntity> user = userService.getUserById(id);
        return user
                .map(userEntity -> ResponseEntity.ok(userEntity.ToContract()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
