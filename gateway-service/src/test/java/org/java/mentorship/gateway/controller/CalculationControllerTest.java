package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.calculation.client.CalculationFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CalculationControllerTest {

    @Mock
    private CalculationFeignClient calculationFeignClient;

    @InjectMocks
    private CalculationController calculationController;

    @Test
    void helloShouldReturnHello() {
        String response = calculationController.hello();

        verify(calculationFeignClient).hello();
    }

}
