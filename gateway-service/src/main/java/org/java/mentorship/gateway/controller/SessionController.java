package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final UserFeignClient userFeignClient;

    @PostMapping()
    public ResponseEntity<Session> createSession(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userFeignClient.createSession(loginRequest));
    }

    @GetMapping("/{key}")
    public ResponseEntity<Session> getSession(@PathVariable(name = "key") String key) {
        return ResponseEntity.ok(userFeignClient.getSession(key));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Session>> getSessionsByUser(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(userFeignClient.getSessions(id));
    }

    @GetMapping("/user/{id}/active")
    public ResponseEntity<List<Session>> getActiveSessionsByUser(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(userFeignClient.getActiveSessions(id));
    }

}
