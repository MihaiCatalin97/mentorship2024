package org.java.mentorship.email.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.java.mentorship.email.dto.EmailRequest;
import org.java.mentorship.email.dto.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    public EmailResponse sendVerification(EmailRequest request, Map<String, Object> model) {

        EmailResponse response = new EmailResponse();
        MimeMessage message = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);

            Template template = config.getTemplate("email-verification.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(request.getTo());
            helper.setFrom(username);
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            sender.send(message);

            response.setMessage("Mail sent to " + request.getTo());
            response.setConfirmation(Boolean.TRUE);
        } catch (MessagingException | IOException | TemplateException e) {}
        return response;
    }
}
