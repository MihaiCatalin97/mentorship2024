package org.java.mentorship.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserApplicationTest {

    @Test
    void mainShouldStartApplication() {
        Assertions.assertDoesNotThrow(() -> UserApplication.main(new String[]{}));
    }
}
