package org.java.mentorship.user.domain.mapper;

import lombok.NoArgsConstructor;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.user.domain.UserEntity;

@NoArgsConstructor
public class UserContractMapper {

    public static User userToContract(UserEntity userEntity) {
        final User userContract = new User();

        userContract.setId(userEntity.getId());
        userContract.setFirstName(userEntity.getFirstName());
        userContract.setLastName(userEntity.getLastName());
        userContract.setVerified(userEntity.getVerified());
        userContract.setEmail(userEntity.getEmail());

        return userContract;
    }

}
