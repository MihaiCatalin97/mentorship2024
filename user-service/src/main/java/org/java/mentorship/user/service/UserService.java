package org.java.mentorship.user.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.UserRegistrationRequest;
import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository repository;

    public String getMd5(String plaintext)
    {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        m.reset();
        m.update(plaintext.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);

        hashtext = "0".repeat(32 - hashtext.length()) + hashtext;
        return hashtext;
    }

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
        System.out.println(user);
        return repository.saveUser(user);
    }

    public boolean verifyUserHash(Integer id, String password) {
        Optional<UserEntity> user = getUserById(id);
        return user
                .map(userEntity -> userEntity.getHashedPassword().equals(getMd5(password)))
                .orElse(false);
    }
}
