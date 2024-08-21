package org.java.mentorship.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.java.mentorship.user.domain.UserEntity;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {
    List<UserEntity> find();

    Optional<UserEntity> findById(Integer id);

    Optional<UserEntity> findByEmail(String email);

    void insert(UserEntity user);

    boolean setUserVerifiedStatus(@Param("id") Integer id, @Param("verified") Boolean verified);
}
