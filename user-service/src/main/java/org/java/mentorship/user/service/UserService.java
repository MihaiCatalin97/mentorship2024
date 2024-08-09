package org.java.mentorship.user.service;

import org.java.mentorship.user.domain.UserEntity;
import org.java.mentorship.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository repository;

    public List<UserEntity> getAllUsers() {
        return repository.getAllUsers();
    }

    public Optional<UserEntity> getUserById(Integer id) {
        return repository.getUserById(id);
    }
}
