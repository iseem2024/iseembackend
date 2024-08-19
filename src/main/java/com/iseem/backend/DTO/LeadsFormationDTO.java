package com.iseem.backend.DTO;

import org.springframework.data.domain.Page;
import com.iseem.backend.Entities.Lead;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeadsFormationDTO {
    private String nom;
    Page<Lead> leads;
}
