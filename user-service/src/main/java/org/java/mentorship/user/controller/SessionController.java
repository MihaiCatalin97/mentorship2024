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

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getSessions(@RequestParam(name = "userId", required = false) Integer userId,
                                                     @RequestParam(name = "isActive", required = false) Boolean isActive) {
        return ResponseEntity.ok(sessionService.find(userId, isActive));
    }

    @GetMapping("/sessions/{key}")
    public ResponseEntity<Session> getSession(@PathVariable(name = "key") String key) {
        return sessionService.getSession(key)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
