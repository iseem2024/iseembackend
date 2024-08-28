package com.iseem.backend.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomUtilisateur;
    private String nom;
    private String prenom;
    private String telephone;
    private String whatsapp;
    private String email;
    private String niveau;
    private boolean interested;
    private boolean called;
    private LocalDateTime dateImportation = LocalDateTime.now();

    
    @ManyToOne
    private Formation formation;

    @OneToMany(mappedBy = "lead")
    private List<Appel> appels;

    @ManyToOne
    private User user;
    
}
