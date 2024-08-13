package org.java.mentorship.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailApplicationTest  {

    @Test
    void mainShouldStartApplication() {
        Assertions.assertDoesNotThrow(() -> EmailApplication.main(new String[]{}));
    }
}