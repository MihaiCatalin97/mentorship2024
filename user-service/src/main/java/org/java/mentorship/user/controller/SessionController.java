package org.java.mentorship.user.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.SessionWithKey;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.java.mentorship.user.domain.mapper.SessionContractMapper;
import org.java.mentorship.user.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/sessions")
    public ResponseEntity<SessionWithKey> createSession(@RequestBody LoginRequest loginRequest) {
        return sessionService.createSession(loginRequest)
                .map(session -> ResponseEntity.ok(SessionContractMapper.sessionToContractWithKey(session)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getSessions(@RequestParam(name = "userId", required = false) Integer userId,
                                                     @RequestParam(name = "isActive", required = false) Boolean isActive) {
        return ResponseEntity.ok(sessionService.find(userId, isActive)
                .stream().map(SessionContractMapper::sessionToContract).collect(Collectors.toList()));
    }

    @GetMapping("/sessions/{key}")
    public ResponseEntity<Session> getSession(@PathVariable(name = "key") String key) {
        return sessionService.getSession(key)
                .map(session -> ResponseEntity.ok(SessionContractMapper.sessionToContract(session)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
