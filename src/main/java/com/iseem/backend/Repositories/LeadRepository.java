package com.iseem.backend.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Formation;
import com.iseem.backend.Entities.Lead;
import java.time.LocalDate;


@Repository
public interface LeadRepository extends JpaRepository<Lead, Integer>{
    List<Lead> findByDateImportation(LocalDate dateImportation);
    List<Lead> findByDateImportationAndCalledFalse(LocalDate dateImportation);
    List<Lead> findByCalledFalseOrderByDateImportationAsc();
    Page<Lead> findByFormationOrderByDateImportationDesc(Formation formation, Pageable pageable);
    Page<Lead> findByFormationAndNomUtilisateurContainingOrTelephoneContainingOrderByDateImportationDesc(Formation formation, String nomUtilisateur, String telephone, Pageable pageable);
    boolean existsByTelephone(String telephone);
    boolean existsByTelephoneAndFormation(String telephone, Formation formation);
    Lead findByTelephone(String telephone);
    List<Lead> findByTelephoneOrWhatsappOrderByDateImportationAsc(String telephone, String whatsapp);
}
