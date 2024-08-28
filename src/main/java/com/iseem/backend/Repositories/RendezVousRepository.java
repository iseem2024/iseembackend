package com.iseem.backend.Repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Entities.RendezVous;
import com.iseem.backend.Entities.User;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Integer> {
    RendezVous findByLeadAndAnnuleFalse(Lead lead);

    List<RendezVous> findByDateAndEstFait(LocalDate date, boolean estFait);

    List<RendezVous> findByEstFait(boolean estFait);

    List<RendezVous> findByAnnuleTrue();

    List<RendezVous> findByModification(boolean modification);

    List<RendezVous> findByDate(LocalDate date);

    List<RendezVous> findByUserAndDate(User user, LocalDate date);

    List<RendezVous> findRendezVoussByDateBetween(LocalDate startOfPeriod, LocalDate endOfPeriod);

    Page<RendezVous> findByUserAndDateCreationBetween(User user, LocalDateTime startOfPeriod, LocalDateTime endOfPeriod, Pageable pageable);
}
