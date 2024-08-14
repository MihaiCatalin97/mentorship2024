package org.java.mentorship.user.domain.mapper;

import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.user.crypt.MD5;
import org.java.mentorship.user.domain.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserContractMapperTest {

    @Test
    void userToContractShouldReturnContractWithAllFields() {
        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .email("admin@localhost")
                .firstName("First Name")
                .lastName("Last Name")
                .hashedPassword(MD5.getMd5("Secret Password"))
                .verificationToken(UUID.randomUUID().toString())
                .verified(false)
                .build();

        User user = UserContractMapper.userToContract(userEntity);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getFirstName(), user.getFirstName());
        assertEquals(userEntity.getLastName(), user.getLastName());
        assertEquals(userEntity.getVerified(), user.getVerified());
    }

}
