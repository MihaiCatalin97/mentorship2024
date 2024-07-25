package org.java.mentorship.controller;

import org.java.mentorship.service.TestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService,
                          @Qualifier("mapOfProperties") Map<String, String> properties) {
        this.testService = testService;
    }
}
