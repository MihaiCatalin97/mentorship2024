package org.java.mentorship.notification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotificationApplicationTest {

    @Test
    void mainShouldStartApplication() {
        Assertions.assertDoesNotThrow(() -> NotificationApplication.main(new String[]{}));
    }
}
