package org.java.mentorship.email.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.contracts.email.dto.EmailRequest;
import org.java.mentorship.email.config.EmailConfiguration;
import org.java.mentorship.email.exception.domain.EmailServiceException;
import org.java.mentorship.email.validator.AbstractPayloadEmailValidator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEmailService {

    private final EmailConfiguration emailConfiguration;

    private final Configuration config;

    private final JavaMailSender sender;

    private final AbstractPayloadEmailValidator validator;

    public void send(final EmailRequest request) {
        log.info("Sending email {}", request);
        validator.validate(request.getPayload());

        MimeMessage message = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
            Template template = config.getTemplate(getTemplateFile());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, request.getPayload());

            helper.setTo(request.getEmail());
            helper.setFrom(emailConfiguration.getUsername());
            helper.setText(html, true);
            helper.setSubject(getSubject());
            sender.send(message);
        } catch (Exception e) {
            log.error("Error sending email", e);
            throw new EmailServiceException(INTERNAL_SERVER_ERROR, "Error sending email: " + e.getMessage());
        }
    }

    public abstract boolean canHandle(final EmailRequest request);

    protected abstract String getSubject();

    protected abstract String getTemplateFile();
}
