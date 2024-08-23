package org.java.mentorship.calculation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculationController {

    @GetMapping
    public String hello() {
        return "Hello World!";
    }

}
