package com.instagram.Instagram.clone.controller;


import com.instagram.Instagram.clone.model.RegistrationMail;
import com.instagram.Instagram.clone.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;


@RestController
@CrossOrigin
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/registration/{receiverMail}")
    public String sendRegistration(
            @RequestParam String receiverMail,
            @RequestBody RegistrationMail registrationMail) throws MessagingException {

        Context context = new Context();
        context.setVariable("name", registrationMail.getName());
        context.setVariable("subject", registrationMail.getSubject());

        mailService.sendRegistrationEmail(receiverMail, registrationMail, "registrationMailTemplate", context);
        return "Successfully sent the mail!!";
    }


}
