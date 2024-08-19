package com.iseem.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Appel;

@Repository
public interface AppelRepository extends JpaRepository<Appel, Integer>{
    
} 
