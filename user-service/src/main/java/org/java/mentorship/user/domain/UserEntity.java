package org.java.mentorship.user.domain;

import lombok.Data;
import org.java.mentorship.contracts.user.dto.User;

@Data
public class UserEntity {
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;

    public User ToContract() {
        final User userContract = new User();

        userContract.setId(this.id);
        userContract.setFirstName(this.firstName);
        userContract.setLastName(this.lastName);

        return userContract;
    }
}
