package org.java.mentorship.user.repository;

import org.java.mentorship.user.crypt.MD5;
import org.java.mentorship.user.domain.UserEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MybatisTest
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findShouldReturnAllUsers() {
        List<UserEntity> users = userRepository.find();

        assertEquals(2, users.size());
    }

    @Test
    void findByIdShouldReturnUserWithId() {
        Optional<UserEntity> user = userRepository.findById(1);

        assertTrue(user.isPresent());
        assertEquals(1, user.get().getId());
    }

    @Test
    void findByEmailShouldReturnUserWithEmail() {
        Optional<UserEntity> user = userRepository.findByEmail("admin@localhost.com");

        assertTrue(user.isPresent());
        assertEquals("admin@localhost.com", user.get().getEmail());
    }

    @Test
    void insertShouldInsertUser() {
        UserEntity user = UserEntity.builder()
                .verificationToken("AA-BB-CC")
                .verified(false)
                .firstName("First Name")
                .lastName("Last Name")
                .email("email10@localhost.com")
                .hashedPassword(MD5.getMd5("Secret"))
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        userRepository.insert(user);

        assertEquals(3, userRepository.find().size());
    }
}