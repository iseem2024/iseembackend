package com.iseem.backend.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
public class Inscription {
    @EmbeddedId
    private InscriptionPKA inscriptionPKA;
    private LocalDateTime dateInscription;
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private LocalDate dateFin;
    private int duree;
    private String uniteDuree;
    private float prixTotal;
    private float prixInscription;

    @ManyToOne
    @JoinColumn(name="client",referencedColumnName="id",insertable = false,updatable = false)
    private Client client;
    
    @ManyToOne
    @JoinColumn(name="formation",referencedColumnName="id",insertable = false,updatable = false)
    private Formation formation;

    @OneToMany(mappedBy = "inscription")
    private List<Payment> payments;

    @ManyToOne
    private User user;
}
