package com.example.backend.service;

import com.example.backend.model.RegistrationMail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService {

    @Autowired
    public JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("$(Instagram Registration)")
    private String registrationMailSender;

    @Async
    public void sendRegistrationEmail(String receiverMail, RegistrationMail registrationMail, String template, Context context) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String htmlContent = templateEngine.process(template, context);

        helper.setTo(receiverMail);
        helper.setSubject(registrationMail.getSubject() != null ? registrationMail.getSubject() : "Registration Confirmation");
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }

}

