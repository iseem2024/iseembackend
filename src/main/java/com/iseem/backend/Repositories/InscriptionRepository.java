package com.iseem.backend.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.InscriptionPKA;
import com.iseem.backend.Entities.User;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, InscriptionPKA>{
    List<Inscription> findAllByOrderByDateInscriptionDesc();
    
    Page<Inscription> findByUserAndDateInscriptionBetweenOrderByDateInscriptionDesc(User user, LocalDateTime startOfPeriod, LocalDateTime endOfPeriod, Pageable pageable);}
