package com.iseem.backend.Services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iseem.backend.DTO.LeadDTO;
import com.iseem.backend.DTO.LeadDTO;
import com.iseem.backend.DTO.LeadDuplicationDTO;
import com.iseem.backend.DTO.LeadsFormationDTO;
import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Entities.Formation;
import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.LeadRepository;
import com.iseem.backend.dao.IDao;

@Service
public class LeadService implements IDao<Lead> {

    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private FormationService formationService;
    @Autowired
    private UserService userService;

    public LeadDTO convertToDTO(Lead lead) {
        return new LeadDTO(
            lead.getNom(),
            lead.getPrenom(),
            lead.getTelephone(),
            lead.getDateImportation(),
            lead.getFormation().getNom()
                );
    }

    @Override
    public Lead findById(int id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lead not found with id: " + id));
    }

    @Override
    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    @Override
    public Lead create(Lead lead) {
        boolean leadExists = leadRepository.existsByTelephoneAndFormation(lead.getTelephone(), lead.getFormation());

        if (leadExists) {
            throw new IllegalArgumentException(
                    "Lead already exists with this telephone number for the given formation.");
        }

        lead.setCalled(false);
        lead.setDateImportation(LocalDateTime.now());
        return leadRepository.save(lead);
    }

    public LeadDuplicationDTO createLeads(List<Lead> leads, int formationId) {
        Formation formation = formationService.findById(formationId);
        List<Lead> leads2 = new ArrayList<>();
        int nbrDuplication = 0;
        for (Lead lead : leads) {
            if (!leadRepository.existsByTelephoneAndFormation(lead.getTelephone(), formation)) {
                lead.setFormation(formation);
                leads2.add(lead);
            } else {
                nbrDuplication++;
            }
        }
        List<Lead> savedLeads = leadRepository.saveAll(leads2);
        return new LeadDuplicationDTO(savedLeads, nbrDuplication);
    }
    // public LeadDuplicationDTO createLeads(List<Lead> leads, int formationId) {
    // Formation formation = formationService.findById(formationId);
    // List<Lead> leads2 = new ArrayList<>();
    // int nbrDuplication = 0;
    // for (Lead lead : leads) {
    // if(leadRepository.existsByTelephone(lead.getTelephone())){
    // if (!leadRepository.existsByTelephoneAndFormations(lead.getTelephone(),
    // formation)) {
    // Lead l = leadRepository.findByTelephone(lead.getTelephone());
    // l.getFormations().add(formation);
    // leads2.add(lead);
    // } else {
    // nbrDuplication++;
    // }
    // }
    // else{
    // lead.getFormations().add(formation);
    // leads2.add(lead);
    // }

    // }
    // List<Lead> savedLeads = leadRepository.saveAll(leads2);
    // return new LeadDuplicationDTO(savedLeads, nbrDuplication);
    // }

    @Override
    public Lead update(Lead lead) {
        Lead existingLead = leadRepository.findById(lead.getId())
                .orElseThrow(() -> new NotFoundException("Lead not found with id: " + lead.getId()));
        existingLead.setNomUtilisateur(lead.getNomUtilisateur());
        existingLead.setNom(lead.getNom());
        existingLead.setPrenom(lead.getPrenom());
        existingLead.setTelephone(lead.getTelephone());
        existingLead.setEmail(lead.getEmail());
        existingLead.setWhatsapp(lead.getWhatsapp());
        existingLead.setInterested(lead.isInterested());
        existingLead.setNiveau(lead.getNiveau());
        return leadRepository.save(existingLead);
    }

    @Override
    public void delete(int id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lead not found with id: " + id));
        leadRepository.delete(lead);
    }

    public Lead markLeadInterested(int id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lead not found with id: " + id));
        lead.setInterested(true);
        return leadRepository.save(lead);
    }

    public Lead markLeadCalled(int id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lead not found with id: " + id));
        lead.setCalled(true);
        return leadRepository.save(lead);
    }

    public List<Lead> findLeadsByDate(LocalDate date) {
        return leadRepository.findByDateImportation(date);
    }

    public List<Lead> findUncontactedLeadsByDate(LocalDate date) {
        return leadRepository.findByDateImportationAndCalledFalse(date);
    }

    public List<Lead> findAllUncontactedLeadsOrderByDateDesc() {
        return leadRepository.findByCalledFalseOrderByDateImportationAsc();
    }

    public LeadsFormationDTO findLeadsByinformationId(int id, String nomUtilisateur, String telephone, int page,
            int size) {
        Formation formation = formationService.findById(id);
        Pageable pageable = PageRequest.of(page, size);
        if (nomUtilisateur == null && telephone == null) {
            return new LeadsFormationDTO(formation.getNom(),
                    leadRepository.findByFormationOrderByDateImportationDesc(formation, pageable));
        } else
            return new LeadsFormationDTO(formation.getNom(),
                    leadRepository
                            .findByFormationAndNomUtilisateurContainingOrTelephoneContainingOrderByDateImportationDesc(
                                    formation, nomUtilisateur, telephone, pageable));
    }

    public Lead searchLead(String telephone) {
        List<Lead> leads = leadRepository.findByTelephoneOrWhatsappOrderByDateImportationAsc(telephone, telephone);
        if (leads.isEmpty())
            throw new NotFoundException("Lead not found with telephone: " + telephone);
        else
            return leads.get(0);
    }

    public Page<LeadDTO> getLeadsByDay(int userId, LocalDate date, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        Page<Lead> leadsPage = leadRepository.findByUserAndDateImportationBetweenOrderByDateImportationDesc(user, startOfDay, endOfDay,pageable);
        return leadsPage.map(this::convertToDTO);
    }

    public Page<LeadDTO> getLeadsByMonth(int userId, YearMonth yearMonth, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        Page<Lead> leadsPage = leadRepository.findByUserAndDateImportationBetweenOrderByDateImportationDesc(user, startOfMonth, endOfMonth,
                pageable);
        return leadsPage.map(this::convertToDTO);
    }

    public Page<LeadDTO> getLeadsByYear(int userId, Year year, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfYear = year.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = year.atMonth(12).atEndOfMonth().atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        Page<Lead> leadsPage = leadRepository.findByUserAndDateImportationBetweenOrderByDateImportationDesc(user, startOfYear, endOfYear,
                pageable);
        return leadsPage.map(this::convertToDTO);
    }

    public Page<LeadDTO> getLeadsByCurrentWeek(int userId, int page, int size) {
        User user = userService.findById(userId);
        LocalDate today = LocalDate.now();
        LocalDateTime startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atStartOfDay();
        Pageable pageable = PageRequest.of(page, size);
        Page<Lead> leadsPage = leadRepository.findByUserAndDateImportationBetweenOrderByDateImportationDesc(user, startOfWeek, endOfWeek,
                pageable);
        return leadsPage.map(this::convertToDTO);
    }

}
