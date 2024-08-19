package com.iseem.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.InscriptionPKA;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, InscriptionPKA>{
    
}
