package com.iseem.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationDTO {
    private int id;
    private String nom;
    private long nbrLeads;
}
