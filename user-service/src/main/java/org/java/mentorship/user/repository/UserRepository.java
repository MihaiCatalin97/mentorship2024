package org.java.mentorship.user.repository;

import org.java.mentorship.user.domain.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<UserEntity> users = new ArrayList<>();
    private Integer currentId = 0;

    public List<UserEntity> getAllUsers() {
        return users;
    }

    public Optional<UserEntity> getUserById(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findAny();
    }

    public UserEntity saveUser(UserEntity user) {
        user.setId(++currentId);
        users.add(user);
        return user;
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

    public boolean setUserVerified(Integer id) {
        users.stream().filter(user -> user.getId().equals(id)).findAny().ifPresent(user -> {
            user.setVerified(true);
        });
        return true;
    }
}
