package com.iseem.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iseem.backend.DTO.LoginUserDto;
import com.iseem.backend.DTO.RegisterUserDto;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Exceptions.AlreadyExistException;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.UserRepository;
import com.iseem.backend.Services.EmailService;
import com.iseem.backend.Utils.PasswordGenerator;
import com.iseem.backend.Utils.SendEmail;

import jakarta.mail.MessagingException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) throws MessagingException {
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new AlreadyExistException("Email is already registered");
        }

        User user = new User();
        user.setNom(input.getNom());
        user.setPrenom(input.getPrenom());
        user.setEmail(input.getEmail());
        user.setRole("ADMIN");
        String password = PasswordGenerator.generatePassword(8);
        user.setPassword(passwordEncoder.encode(password));
        emailService.sendEmailRegistraction(input, password);;

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }
}
