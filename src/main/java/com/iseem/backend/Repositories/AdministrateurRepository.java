package com.iseem.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Administrateur;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Integer> {
   
}
