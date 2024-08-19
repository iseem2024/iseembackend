package com.iseem.backend.Controllers;

import com.iseem.backend.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        String htmlBody = "<!DOCTYPE html><html><head><style>.container {font-family: Arial, sans-serif; padding: 20px;} .header {background-color: #4CAF50; color: white; padding: 10px; text-align: center;} .content {margin: 20px 0;} .footer {background-color: #f1f1f1; color: #888; padding: 10px; text-align: center;}</style></head><body><div class='container'><div class='header'><h1>Test Email</h1></div><div class='content'><p>Bonjour,</p><p>" + body + "</p></div><div class='footer'><p>&copy; 2024 Votre ISEEM</p></div></div></body></html>";

        try {
            emailService.sendHtmlEmail(to, subject, htmlBody);
            return "Email sent successfully";
        } catch (MessagingException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
