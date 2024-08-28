package com.iseem.backend.Entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private int duree;
    private String uniteDuree;
    @Temporal(TemporalType.DATE)
    private LocalDate dateDebut;
    @Temporal(TemporalType.DATE)
    private LocalDate dateFin;
    @Column(name = "formation_condition")
    private String condition;
    private String type;
    
    @OneToMany(mappedBy = "formation")
    @JsonIgnore
    private List<Lead> leads;
    
    @OneToMany(mappedBy = "formation")
    @JsonIgnore
    private List<Appel> appels;

}
