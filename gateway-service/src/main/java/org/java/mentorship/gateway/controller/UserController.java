package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.request.PasswordChangeRequest;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.contracts.user.dto.request.SendPasswordChangeTokenRequest;
import org.java.mentorship.contracts.user.dto.request.SendVerificationTokenRequest;
import org.java.mentorship.gateway.security.authorization.UserAuthorization;
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
        UserAuthorization.loggedInAsUser(null);
        return ResponseEntity.ok(userFeignClient.getUsers());
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable(name = "id") Integer id) {
        UserAuthorization.loggedInAsUser(id);
        return ResponseEntity.ok(userFeignClient.getUser(id));
    }

    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(userFeignClient.registerUser(registrationRequest));
    }

    @PutMapping("/verify/{token}")
    ResponseEntity<Boolean> verifyUser(@PathVariable(name = "token") String token) {
        return ResponseEntity.ok(userFeignClient.verifyUser(token));
    }

    @PostMapping("/verify")
    ResponseEntity<Boolean> sendVerificationToken(@RequestBody SendVerificationTokenRequest sendVerificationTokenRequest) {
        UserAuthorization.loggedInAsUser(sendVerificationTokenRequest.getUserId());
        return ResponseEntity.ok(userFeignClient.sendNotificationToken(sendVerificationTokenRequest));
    }

    @PostMapping("/recovery")
    ResponseEntity<Boolean> sendPasswordChangeRequest(@RequestBody SendPasswordChangeTokenRequest sendPasswordChangeTokenRequest) {
        return ResponseEntity.ok(userFeignClient.sendPasswordChangeRequest(sendPasswordChangeTokenRequest));
    }

    @PutMapping("/recovery/{token}")
    ResponseEntity<Boolean> sendVerificationToken(@PathVariable(name = "token") String token, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        return ResponseEntity.ok(userFeignClient.changePasswordWithToken(token, passwordChangeRequest));
    }
}
