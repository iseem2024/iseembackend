package com.iseem.backend.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String statut;
    private String contenu;
    private LocalDateTime date;
    
    @NotEmpty
    @NotNull
    @JsonIgnore
    @ManyToOne
    private Lead lead;

    @NotEmpty
    @NotNull
    @JsonIgnore
    @ManyToOne
    private Formation formation;

    // @NotEmpty
    // @NotNull
    // @JsonIgnore
    // @ManyToOne
    // private Administrateur administrateur;
}
