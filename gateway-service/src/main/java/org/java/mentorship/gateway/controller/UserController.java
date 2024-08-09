package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.UserRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserFeignClient userFeignClient;

    @GetMapping()
    ResponseEntity<List<User>> getUsers() {
        // TODO: Restrict to admin users
        return ResponseEntity.ok(userFeignClient.getUsers());
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable(name="id") Integer id) {
        // TODO: Users can't read other users
        return ResponseEntity.ok(userFeignClient.getUser(id));
    }

    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        // TODO: Users can't read other users
        return ResponseEntity.ok(userFeignClient.registerUser(registrationRequest));
    }

}
