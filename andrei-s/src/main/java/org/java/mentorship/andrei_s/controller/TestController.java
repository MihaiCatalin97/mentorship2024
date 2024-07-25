package org.java.mentorship.andrei_s.controller;

import org.java.mentorship.andrei_s.service.TestService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    private final TestService testService;

    TestController(TestService testService,
                   Map<String, String> properties)
    {
        this.testService = testService;
    }
}
