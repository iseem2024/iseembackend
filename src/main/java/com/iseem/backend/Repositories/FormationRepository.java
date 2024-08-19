package com.iseem.backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iseem.backend.DTO.FormationDTO;
import com.iseem.backend.Entities.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Integer> {
    @Query("SELECT new com.iseem.backend.DTO.FormationDTO(f.id, f.nom, (SELECT COUNT(l) FROM f.leads l)) " +
            "FROM Formation f")
    List<FormationDTO> findformationWithLeadsCount();
}
