package org.java.mentorship.calculation;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculationApplicationTest {

    @Test
    void mainShouldStartApplication() {
        Assertions.assertDoesNotThrow(() -> CalculationApplication.main(new String[]{}));
    }

}
