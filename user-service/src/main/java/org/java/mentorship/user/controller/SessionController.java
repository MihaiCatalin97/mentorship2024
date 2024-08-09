package org.java.mentorship.user.controller;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.UserLoginRequest;
import org.java.mentorship.user.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/sessions")
    public ResponseEntity<Session> createSession(@RequestBody UserLoginRequest loginRequest) {
        return sessionService.createSession(loginRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/sessions/{key}")
    public ResponseEntity<Session> getSession(@PathVariable(name="key") String key) {
        return sessionService.getSession(key)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
