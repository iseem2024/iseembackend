package com.iseem.backend.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Depense;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Integer> {
    List<Depense> findDepensesByDateDepenseBetween(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod);
}
