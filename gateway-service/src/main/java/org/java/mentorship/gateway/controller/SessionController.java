package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.SessionWithKey;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {
    private final SessionFeignClient sessionFeignClient;

    @PostMapping()
    public ResponseEntity<SessionWithKey> createSession(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(sessionFeignClient.createSession(loginRequest));
    }

    @GetMapping()
    public ResponseEntity<List<Session>> findSessions(@RequestParam(name = "userId", required = false) Integer userId,
                                                      @RequestParam(name = "isActive", required = false) Boolean isActive) {
        return ResponseEntity.ok(sessionFeignClient.find(userId, isActive));
    }

    @GetMapping("/{key}")
    public ResponseEntity<Session> getSession(@PathVariable(name = "key") String key) {
        return ResponseEntity.ok(sessionFeignClient.getSession(key));
    }
}
