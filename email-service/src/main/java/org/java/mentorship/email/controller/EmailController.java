package org.java.mentorship.email.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.email.dto.EmailRequest;
import org.java.mentorship.email.exception.domain.EmailServiceException;
import org.java.mentorship.email.service.AbstractEmailService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@PropertySource("classpath:email.properties")
public class EmailController {

    private final List<AbstractEmailService> emailServices;

    @PostMapping("/emails")
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid EmailRequest request) {
        for (AbstractEmailService service : emailServices) {
            if (service.canHandle(request)) {
                service.send(request);
                return ResponseEntity.ok(null);
            }
        }

        throw new EmailServiceException(INTERNAL_SERVER_ERROR,
                "No email processor found for type " + request.getType().name());
    }
}
