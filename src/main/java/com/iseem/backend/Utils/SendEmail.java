package com.iseem.backend.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.iseem.backend.DTO.RegisterUserDto;
import com.iseem.backend.Entities.Task;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Entities.UserTask;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendEmail {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(message);
    }

    @Async
    public void sendVerificationCode(String to, String code, User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    
        String body = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; line-height: 1.6; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }" +
                ".header { font-size: 20px; font-weight: bold; margin-bottom: 20px; }" +
                ".content { margin-bottom: 20px; }" +
                ".code { font-size: 24px; font-weight: bold; color: #f44336; }" +
                ".footer { font-size: 12px; color: #777; margin-top: 30px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>Code de Vérification</div>" +
                "<div class='content'>" +
                "<p>Bonjour " + user.getNom() + " " + user.getPrenom() + ",</p>" +
                "<p>Nous avons reçu une demande pour réinitialiser votre mot de passe. Veuillez utiliser le code de vérification ci-dessous pour continuer :</p>" +
                "<p class='code'>" + code + "</p>" +
                "<p>Ce code expirera dans 30 minutes.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>Si vous n'avez pas demandé cela, veuillez ignorer cet email.</p>" +
                "<p>Merci,</p>" +
                "<p>L'équipe de votre entreprise</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    
        helper.setTo(to);
        helper.setSubject("Code de Vérification");
        helper.setText(body, true);
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

    @Async
    public void sendTaskEmail(UserTask userTask) throws MessagingException {
        User user = userTask.getUser();
        Task task = userTask.getTask();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String body = "<!DOCTYPE html>" +
                "<html lang=\"fr\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Nouvelle Tâche Assignée</title>" +
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
                "        <h1>Nouvelle Tâche Assignée</h1>" +
                "    </div>" +
                "    <div class=\"content\">" +
                "        <p>Bonjour <strong>" + user.getNom() + " " + user.getPrenom() + "</strong>,</p>" +
                "        <p>Vous avez été assigné à une nouvelle tâche :</p>" +
                "        <div class=\"info\">" +
                "            <p><strong>Titre :</strong> " + task.getTitle() + "</p>" +
                "            <p><strong>Description :</strong> " + task.getDescription() + "</p>" +
                "            <p><strong>Date de Création :</strong> " + task.getCreationDate() + "</p>" +
                "            <p><strong>Priorité :</strong> " + task.getPriority() + "</p>" +
                "        </div>" +
                "        <p>Merci de vérifier et de mettre à jour le statut de votre tâche dès que possible.</p>" +
                "    </div>" +
                "    <div class=\"footer\">" +
                "        <p>Equipe ISEEM !</p>" +
                "    </div>" +
                "</div>" +
                "</body>" +
                "</html>";

        helper.setTo(user.getEmail());
        helper.setSubject("Nouvelle Tâche Assignée - " + task.getTitle());
        helper.setText(body, true);
        mailSender.send(message);
    }

}
