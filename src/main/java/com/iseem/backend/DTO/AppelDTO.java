package com.iseem.backend.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppelDTO {
    private int id;
    private String statut;
    private String contenu;
    private LocalDateTime date;
    private String leadNom;
    private String telephone;
    private String formationNom;

    
}
