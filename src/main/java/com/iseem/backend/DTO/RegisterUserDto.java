package com.iseem.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    private String email;
    
    // private String password;
    
    private String nom;
    private String prenom;
    private String role;
    
}