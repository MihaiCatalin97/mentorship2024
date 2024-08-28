package org.java.mentorship.email.controller;

import org.java.mentorship.email.dto.EmailRequest;
import org.java.mentorship.email.dto.EmailResponse;
import org.java.mentorship.email.dto.EmailType;
import org.java.mentorship.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@PropertySource("classpath:email.properties")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
//    public EmailResponse sendVerification(@RequestParam(name = "email") EmailRequest to,
//                                          @RequestParam(name = "subject") EmailRequest subject,
//                                          @RequestParam(name = "type") EmailType type) {

    public EmailResponse sendVerification(@RequestBody EmailRequest request) {
        Map<String, Object> model = new HashMap<>();

        return emailService.sendVerification(request, model);
    }
}
