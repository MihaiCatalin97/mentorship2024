package org.java.mentorship.calculation.controller;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculationControllerTest {

    private final CalculationController calculationController = new CalculationController();

    @Test
    void helloShouldReturnHello() {
        String response = calculationController.hello();

        assertEquals("Hello World!", response);
    }
}
