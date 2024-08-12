package org.java.mentorship.user.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.UserRegistrationRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.repository.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.java.mentorship.user.crypt.MD5.getMd5;

@Service
@AllArgsConstructor
public class UserService {
//    UserRepository repository;
    UserMapper mapper;

    public List<UserEntity> getAllUsers() {
        return mapper.findAll();
    }

    public Optional<UserEntity> getUserById(Integer id) {
        return mapper.findById(id);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return mapper.findByEmail(email);
    }

    public Optional<UserEntity> registerUser(UserRegistrationRequest registrationRequest) {
        if (getUserByEmail(registrationRequest.getEmail()).isPresent()) {
            return Optional.empty();
        }

        final UserEntity user = new UserEntity();
        user.setEmail(registrationRequest.getEmail());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setVerified(false);
        user.setHashedPassword(getMd5(registrationRequest.getPassword()));
        user.setVerificationToken(UUID.randomUUID().toString());

        // TODO: Call notification service with VERIFICATION message type
        mapper.insertUser(user);

        return Optional.of(user);
    }

    public boolean verifyUserHash(Integer id, String password) {
        Optional<UserEntity> user = getUserById(id);
        System.out.println(user);
        return user
                .map(userEntity -> userEntity.getHashedPassword().equals(getMd5(password)))
                .orElse(false);
    }

    public boolean verifyUserUsingToken(Integer id, String token) {
        Optional<UserEntity> user = getUserById(id);
        if (user.isEmpty()) { return false; }
        if (!user.get().getVerificationToken().equals(token)) { return false; }

        return mapper.setUserVerifiedStatus(id, true);
    }
}
