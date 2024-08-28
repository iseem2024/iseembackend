package com.iseem.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.iseem.backend.DTO.RegisterUserDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    @Async
    public void sendEmailRegistraction(RegisterUserDto user, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String body = "<!DOCTYPE html>" +
                "<html lang=\"fr\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Confirmation de Création de Compte</title>" +
                "<style>" +
                "@import url('https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap');" +
                "body {" +
                "    font-family: 'Lato', sans-serif;" +
                "    background-color: #f4f4f4;" +
                "    margin: 0;" +
                "    padding: 0;" +
                "    color: #333;" +
                "}" +
                ".container {" +
                "    max-width: 600px;" +
                "    margin: 50px auto;" +
                "    background-color: #ffffff;" +
                "    border-radius: 10px;" +
                "    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);" +
                "    padding: 20px;" +
                "}" +
                ".header {" +
                "    text-align: center;" +
                "    padding-bottom: 20px;" +
                "}" +
                ".header svg {" +
                "    width: 50px;" +
                "    height: 50px;" +
                "    fill: #4CAF50;" +
                "}" +
                "h1 {" +
                "    font-size: 24px;" +
                "    color: #4CAF50;" +
                "    margin: 0;" +
                "    padding: 0;" +
                "}" +
                ".content {" +
                "    padding: 20px;" +
                "    line-height: 1.6;" +
                "}" +
                ".info {" +
                "    margin: 20px 0;" +
                "}" +
                ".info p {" +
                "    margin: 10px 0;" +
                "    font-size: 16px;" +
                "}" +
                ".info strong {" +
                "    color: #333;" +
                "}" +
                ".footer {" +
                "    text-align: center;" +
                "    padding-top: 20px;" +
                "    font-size: 14px;" +
                "    color: #888;" +
                "}" +
                ".footer p {" +
                "    margin: 5px 0;" +
                "}" +
                ".footer a {" +
                "    color: #4CAF50;" +
                "    text-decoration: none;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "    <div class=\"header\">" +
                "        <svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\">" +
                "            <path d=\"M12 0C5.37 0 0 5.37 0 12s5.37 12 12 12 12-5.37 12-12S18.63 0 12 0zm-2 17l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z\"/>"
                +
                "        </svg>" +
                "        <h1>Compte Créé avec Succès</h1>" +
                "    </div>" +
                "    <div class=\"content\">" +
                "        <p>Bonjour <strong>" + user.getNom() + " " + user.getPrenom() + "</strong>,</p>" +
                "        <p>Votre compte a été créé avec succès. Vous pouvez désormais vous connecter en utilisant les identifiants ci-dessous :</p>"
                +
                "        <div class=\"info\">" +
                "            <p><strong>Email :</strong> " + user.getEmail() + "</p>" +
                "            <p><strong>Mot de passe :</strong> " + password + "</p>" +
                "        </div>" +
                "        <p>Nous vous recommandons de changer votre mot de passe après votre première connexion pour des raisons de sécurité.</p>"
                +
                "    </div>" +
                "    <div class=\"footer\">" +
                "        <p>Merci d'avoir rejoint ISEEM !</p>" +
                "    </div>" +
                "</div>" +
                "</body>" +
                "</html>";

        helper.setTo(user.getEmail());
        helper.setSubject("Création du Compte Administrateur ISEEM");
        helper.setText(body, true);
        mailSender.send(message);
    }
}
