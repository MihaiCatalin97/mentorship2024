package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.UserLoginRequest;
import org.java.mentorship.contracts.user.dto.UserRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final UserFeignClient userFeignClient;

    @PostMapping()
    public ResponseEntity<Session> createSession(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userFeignClient.createSession(userLoginRequest));
    }

    @GetMapping("/{key}")
    public ResponseEntity<Session> getSession(@PathVariable String key) {
        return ResponseEntity.ok(userFeignClient.getSession(key));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Session>> getSessionsByUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userFeignClient.getSessions(id));
    }

    @GetMapping("/user/{id}/active")
    public ResponseEntity<List<Session>> getActiveSessionsByUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userFeignClient.getActiveSessions(id));
    }

}
