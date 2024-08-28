package com.iseem.backend.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeadDTO {
    private String nom;
    private String prenom;
    private String telephone;
    private LocalDateTime dateImportation;
    private String formationNom;
}
