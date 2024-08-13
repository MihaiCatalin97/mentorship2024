package org.java.mentorship.budget;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BudgetApplicationTest {

    @Test
    void mainShouldStartApplication() {
        Assertions.assertDoesNotThrow(() -> BudgetApplication.main(new String[]{}));
    }
}