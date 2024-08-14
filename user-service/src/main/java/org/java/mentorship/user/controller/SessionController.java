package org.java.mentorship.user.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.java.mentorship.user.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/sessions")
    public ResponseEntity<Session> createSession(@RequestBody LoginRequest loginRequest) {
        return sessionService.createSession(loginRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/sessions/{key}")
    public ResponseEntity<Session> getSession(@PathVariable(name = "key") String key) {
        return sessionService.getActiveSession(key)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/sessions/user/{id}")
    public ResponseEntity<List<Session>> getSessionsByUser(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(sessionService.getSessionsByUser(id));
    }

    @GetMapping("/sessions/user/{id}/active")
    public ResponseEntity<List<Session>> getActiveSessionsByUser(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(sessionService.getActiveSessionsByUser(id));
    }

}
