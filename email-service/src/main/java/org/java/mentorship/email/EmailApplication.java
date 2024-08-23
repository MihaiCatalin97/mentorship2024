package org.java.mentorship.email;

import org.java.mentorship.email.dto.EmailRequest;
import org.java.mentorship.email.dto.EmailResponse;
import org.java.mentorship.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@PropertySource("classpath:email.properties")
public class EmailApplication {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendtest")
    public EmailResponse sendTest(@RequestBody EmailRequest request) {

        Map<String, Object> model = new HashMap<>();

        return emailService.sendTest(request, model);
    }

    @PostMapping("/verify")
    public EmailResponse sendVerification(@RequestBody EmailRequest request) {

        Map<String, Object> model = new HashMap<>();

        return emailService.sendVerification(request, model);
    }

    @PostMapping("/overspend")
    public EmailResponse sendOverSpend(@RequestBody EmailRequest request) {

        Map<String, Object> model = new HashMap<>();

        return emailService.sendOverSpend(request, model);
    }



    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }
}
