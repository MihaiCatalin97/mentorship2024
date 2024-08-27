package org.java.mentorship.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.java.mentorship.user.domain.UserEntity;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {
    List<UserEntity> find();
    Optional<UserEntity> findById(Integer id);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPasswordChangeToken(String token);
    Optional<UserEntity> findByVerificationToken(String token);

    void insert(UserEntity user);
    void update(UserEntity user);
}
