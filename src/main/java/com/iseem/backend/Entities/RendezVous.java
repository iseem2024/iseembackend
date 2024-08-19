package com.iseem.backend.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime heure;
    private LocalDateTime dateCreation;
    private boolean estFait;
    private boolean estInscrit;
    private LocalDateTime dateFait;
    private boolean modification;
    private LocalDateTime dateModification;
    private boolean annule;
    private LocalDateTime dateAnnulation;

    @ManyToOne
    private Lead lead;

    @ManyToOne
    private Administrateur administrateur;
}

