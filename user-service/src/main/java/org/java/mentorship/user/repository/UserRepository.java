package org.java.mentorship.user.repository;

import org.java.mentorship.user.domain.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private List<UserEntity> users;

    public List<UserEntity> getAllUsers() {
        return users;
    }

    public Optional<UserEntity> getUserById(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findAny();
    }
}
