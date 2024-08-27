package org.java.mentorship.user.domain.mapper;

import lombok.experimental.UtilityClass;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.user.domain.UserEntity;

@UtilityClass
public class UserContractMapper {

    public User userToContract(UserEntity userEntity) {
        final User userContract = new User();

        userContract.setId(userEntity.getId());
        userContract.setFirstName(userEntity.getFirstName());
        userContract.setLastName(userEntity.getLastName());
        userContract.setEmail(userEntity.getEmail());
        userContract.setIsAdmin(userEntity.getIsAdmin());
        userContract.setCreatedAt(userEntity.getCreatedAt());
        userContract.setLastSentVerificationNotification(userEntity.getLastSentVerificationNotification());
        userContract.setVerifiedAt(userEntity.getVerifiedAt());
        userContract.setLastChangedPassword(userEntity.getLastChangedPassword());
        userContract.setLastSentPasswordChangeToken(userEntity.getLastSentPasswordChangeToken());

        userContract.setCreatedAt(userEntity.getCreatedAt());
        userContract.setVerifiedAt(userEntity.getVerifiedAt());
        userContract.setLastChangedPassword(userEntity.getLastChangedPassword());

        return userContract;
    }

}
