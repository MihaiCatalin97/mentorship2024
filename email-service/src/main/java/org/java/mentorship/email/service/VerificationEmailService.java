package org.java.mentorship.email.service;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.contracts.email.dto.EmailRequest;
import org.java.mentorship.contracts.email.dto.EmailType;
import org.java.mentorship.email.config.EmailConfiguration;
import org.java.mentorship.email.validator.VerificationEmailValidator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VerificationEmailService extends AbstractEmailService {

    public VerificationEmailService(final EmailConfiguration emailConfiguration,
                                    final Configuration config,
                                    final JavaMailSender sender,
                                    final VerificationEmailValidator validator) {
        super(emailConfiguration, config, sender, validator);
    }

    @Override
    public boolean canHandle(final EmailRequest request) {
        return request.getType().equals(EmailType.VERIFICATION);
    }

    protected String getSubject() {
        return "Budget Tracker - Email verification";
    }

    protected String getTemplateFile() {
        return "email-verification.ftl";
    }
}
