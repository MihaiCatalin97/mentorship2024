package org.java.mentorship.user.repository;

import org.apache.ibatis.annotations.*;
import org.java.mentorship.user.domain.UserEntity;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users")
    List<UserEntity> findAll();

    @Select("SELECT * FROM users where id = #{id}")
    Optional<UserEntity> findById(Integer id);

    @Select("SELECT * FROM users where email = #{email}")
    Optional<UserEntity> findByEmail(String email);

    @Insert("INSERT INTO users(email, first_name, last_name, hashed_password, verification_token) VALUES (#{email}, #{firstName}, #{lastName}, #{hashedPassword}, #{verificationToken})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insertUser(UserEntity user);

    @Update("UPDATE users SET verified = #{verified} WHERE id = #{id}")
    boolean setUserVerifiedStatus(@Param("id") Integer id, @Param("verified") Boolean verified);
}
