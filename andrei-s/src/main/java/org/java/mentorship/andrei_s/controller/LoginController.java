package org.java.mentorship.andrei_s.controller;

import org.java.mentorship.andrei_s.domain.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public String login() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }

    @GetMapping
    public UserDetails get() {
        return UserDetails.builder()
                .username(SecurityContextHolder.getContext().getAuthentication().getName())
                .build();
    }
}
