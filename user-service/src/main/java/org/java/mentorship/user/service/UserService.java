package org.java.mentorship.user.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.UserRegistrationRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.java.mentorship.user.crypt.MD5.getMd5;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository repository;

    public List<UserEntity> getAllUsers() {
        return repository.getAllUsers();
    }

    public Optional<UserEntity> getUserById(Integer id) {
        return repository.getUserById(id);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    public boolean registerUser(UserRegistrationRequest registrationRequest) {
        if (getUserByEmail(registrationRequest.getEmail()).isPresent()) {
            return false;
        }

        final UserEntity user = new UserEntity();
        user.setEmail(registrationRequest.getEmail());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setVerified(false);
        user.setHashedPassword(getMd5(registrationRequest.getPassword()));

        return repository.saveUser(user);
    }

    public boolean verifyUserHash(Integer id, String password) {
        Optional<UserEntity> user = getUserById(id);
        return user
                .map(userEntity -> userEntity.getHashedPassword().equals(getMd5(password)))
                .orElse(false);
    }
}
