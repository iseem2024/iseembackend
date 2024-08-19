package com.iseem.backend.DTO;

import java.util.List;

import com.iseem.backend.Entities.Lead;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeadDuplicationDTO {
    private List<Lead> leads;
    private int nbrDuplication;
}
