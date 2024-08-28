package com.iseem.backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iseem.backend.DTO.PasswordUpdateRequest;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Services.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/v0/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User administrateur = userService.findById(id);
        return ResponseEntity.ok(administrateur);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> administrateurs = userService.findAll();
        return ResponseEntity.ok(administrateurs);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        List<User> users = userService.findAdminUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable int id,
            @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        String newPassword = passwordUpdateRequest.getPassword();
        userService.updatePassword(id, newPassword);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/block/{id}")
    public ResponseEntity<User> handeleBlock(@PathVariable int id) {
        User user = userService.handleBlockAcount(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> initiatePasswordReset(@RequestParam String email) throws MessagingException {
        userService.initiatePasswordReset(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCodeAndResetPassword(@RequestParam String email,
                                                           @RequestParam String code,
                                                           @RequestParam String newPassword) {
        userService.verifyCodeAndResetPassword(email, code, newPassword);
        return ResponseEntity.noContent().build();
    }

}
