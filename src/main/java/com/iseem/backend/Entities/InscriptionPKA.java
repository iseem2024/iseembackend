package com.iseem.backend.Entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class InscriptionPKA implements Serializable{
    private int client;
    private int formation;
}
