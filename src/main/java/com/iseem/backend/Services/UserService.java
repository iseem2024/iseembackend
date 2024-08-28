package com.iseem.backend.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.User;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.UserRepository;
import com.iseem.backend.Utils.PasswordHash;
import com.iseem.backend.Utils.SendEmail;
import com.iseem.backend.dao.IDao;

import jakarta.mail.MessagingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SendEmail emailService;

    public User findById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id : " + id));
        return user;
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> !user.getRole().equals("SUPER"))
                .collect(Collectors.toList());
    }

    public List<User> findAdminUsers() {
        return userRepository.findByRole("ADMIN");
    }

    public User update(int userId, User newUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id : " + userId));
        user.setNom(newUser.getNom());
        user.setPrenom(newUser.getPrenom());
        user.setEmail(newUser.getEmail());
        
        return userRepository.save(user);
    }

    public void updatePassword(int userId, String password){
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found with id : " + userId));
        user.setPassword(PasswordHash.generateHash(password));
        userRepository.save(user);
    }

    public User handleBlockAcount(int userId) {
        User user = findById(userId);
        user.setBlock(!user.isBlock());
        return userRepository.save(user);
    }

    public void initiatePasswordReset(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        String verificationCode = generateVerificationCode();
        String hashedCode = PasswordHash.generateHash(verificationCode);

        user.setVerificationCode(hashedCode);
        user.setCodeExpirationTime(LocalDateTime.now().plusMinutes(30));
        userRepository.save(user);

        emailService.sendVerificationCode(user.getEmail(), verificationCode, user);
    }

    public void verifyCodeAndResetPassword(String email, String code, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    
        if (user.getCodeExpirationTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Verification code has expired.");
        }
    
        if (!PasswordHash.verifyPwd(code, user.getVerificationCode())) {
            throw new IllegalArgumentException("Invalid verification code.");
        }
    
        user.setPassword(PasswordHash.generateHash(newPassword));
        user.setVerificationCode(null);
        user.setCodeExpirationTime(null);
        userRepository.save(user);
    }
    

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Generates a 4-digit code
        return String.valueOf(code);
    }

}
