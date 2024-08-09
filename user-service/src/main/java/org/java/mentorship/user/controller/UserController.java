package org.java.mentorship.user.controller;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.UserRegistrationRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.domain.mapper.UserContractMapper;
import org.java.mentorship.user.service.SessionService;
import org.java.mentorship.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController {
    private final SessionService sessionService;
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(
                userService
                        .getAllUsers()
                        .stream()
                        .map(UserContractMapper::userToContract)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Integer id) {
        Optional<UserEntity> user = userService.getUserById(id);
        return user
                .map(userEntity -> ResponseEntity.ok(UserContractMapper.userToContract(userEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

        return ResponseEntity.ok(Collections.singletonList(user));
    }
}
