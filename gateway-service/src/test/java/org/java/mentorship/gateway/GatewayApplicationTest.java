package org.java.mentorship.gateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GatewayApplicationTest {

    @Test
    void mainShouldStartApplication() {
        Assertions.assertDoesNotThrow(() -> GatewayApplication.main(new String[]{}));
    }
}
