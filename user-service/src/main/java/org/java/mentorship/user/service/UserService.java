package org.java.mentorship.user.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.exception.domain.AlreadyRegisteredException;
import org.java.mentorship.user.exception.domain.UserNotFoundException;
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

    public Optional<UserEntity> registerUser(RegistrationRequest registrationRequest) {
        if (getUserByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new AlreadyRegisteredException();
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

        return mapper.findByEmail(user.getEmail());
    }

    public boolean verifyUserHash(Integer id, String password) {
        Optional<UserEntity> user = getUserById(id);
        return user
                .map(userEntity -> userEntity.getHashedPassword().equals(getMd5(password)))
                .orElse(false);
    }

    public boolean verifyUserUsingToken(Integer id, String token) {
        Optional<UserEntity> user = getUserById(id);
        if (user.isEmpty()) throw new UserNotFoundException();
        if (!user.get().getVerificationToken().equals(token)) { return false; }

        return mapper.setUserVerifiedStatus(id, true);
    }
}
