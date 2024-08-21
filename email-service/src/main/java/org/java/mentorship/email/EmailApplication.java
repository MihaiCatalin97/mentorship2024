package org.java.mentorship.email;

import org.java.mentorship.email.dto.EmailRequest;
import org.java.mentorship.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class EmailApplication {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendtest")
    public EmailService sendEmail(@RequestBody EmailRequest request) {

        Map<String,Object> model = new HashMap<>();

        emailService.sendEmail(request,model);
        return emailService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }
}
