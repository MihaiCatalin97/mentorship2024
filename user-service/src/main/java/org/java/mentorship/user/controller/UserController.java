package org.java.mentorship.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.request.PasswordChangeRequest;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.contracts.user.dto.request.SendPasswordChangeTokenRequest;
import org.java.mentorship.contracts.user.dto.request.SendVerificationTokenRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.domain.mapper.UserContractMapper;
import org.java.mentorship.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(
                userService
                        .getAllUsers()
                        .stream()
                        .map(UserContractMapper::userToContract)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Integer id) {
        Optional<UserEntity> user = userService.getUserById(id);
        return user
                .map(userEntity -> ResponseEntity.ok(UserContractMapper.userToContract(userEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return userService.registerUser(registrationRequest).map(userEntity -> ResponseEntity.ok(UserContractMapper.userToContract(userEntity)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/verify/{token}")
    public ResponseEntity<Boolean> verifyUser(@PathVariable(name = "token") String token) {
        return ResponseEntity.ok(userService.verifyUserUsingToken(token));
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> sendVerificationToken(@RequestBody SendVerificationTokenRequest sendVerificationTokenRequest) {
        return ResponseEntity.ok(userService.resendVerificationToken(sendVerificationTokenRequest.getUserId()));
    }

    @PostMapping("/recovery")
    public ResponseEntity<Boolean> requestChangePassword(@RequestBody SendPasswordChangeTokenRequest sendPasswordChangeTokenRequest) {
        return ResponseEntity.ok(userService.requestChangePasswordToken(sendPasswordChangeTokenRequest.getUserId()));
    }

    @PutMapping("/recovery/{token}")
    public ResponseEntity<Boolean> changePasswordWithToken(@PathVariable(name = "token") String token,
                                                           @RequestBody @Valid PasswordChangeRequest passwordChangeRequest) {
        return ResponseEntity.ok(userService.changePasswordWithToken(
                passwordChangeRequest.getPassword(),
                token)
        );
    }

    @PostMapping("/verify/{id}")
    public ResponseEntity<Boolean> resendVerificationToken(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(userService.resendVerificationToken(id));
    }

}
