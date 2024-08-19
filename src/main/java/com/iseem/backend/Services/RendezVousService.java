package com.iseem.backend.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Entities.RendezVous;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Exceptions.RendezVousExistsException;
import com.iseem.backend.Repositories.AdministrateurRepository;
import com.iseem.backend.Repositories.RendezVousRepository;
import com.iseem.backend.dao.IDao;

@Service
public class RendezVousService implements IDao<RendezVous> {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private LeadService leadService;

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Override
    public RendezVous findById(int id) {
        return rendezVousRepository.findById(id)
                                   .orElseThrow(() -> new NotFoundException("RendezVous not found with id: " + id));
    }

    @Override
    public List<RendezVous> findAll() {
        return rendezVousRepository.findAll();
    }

    @Override
    public RendezVous create(RendezVous rendezVous) {
        Lead lead = leadService.findById(rendezVous.getLead().getId());
        administrateurRepository.findById(rendezVous.getAdministrateur().getId())
                                    .orElseThrow(() -> new NotFoundException("Administrator not found with id : " + rendezVous.getAdministrateur().getId()));
        RendezVous existingRendezVous = rendezVousRepository.findByLeadAndAnnuleFalse(lead);
        
        if (existingRendezVous != null ) {
            throw new RendezVousExistsException("This lead already has a appointment: " + existingRendezVous.getId(), existingRendezVous);
        }
        rendezVous.setDateCreation(LocalDateTime.now());
        rendezVous.setEstFait(false);
        rendezVous.setEstInscrit(false);
        rendezVous.setModification(false);
        rendezVous.setAnnule(false);
        rendezVous.setDateFait(null);
        rendezVous.setDateModification(null);
        rendezVous.setDateAnnulation(null);
        return rendezVousRepository.save(rendezVous);
    }

    public Object getByLeadId(int leadId) {
        Lead lead = leadService.findById(leadId);
        RendezVous rendezVous = rendezVousRepository.findByLeadAndAnnuleFalse(lead);
        if(rendezVous == null)
            return null;
        else
            return rendezVous;
    }

    @Override
    public RendezVous update(RendezVous rendezVous) {
        RendezVous existingRendezVous = rendezVousRepository.findById(rendezVous.getId())
                                                             .orElseThrow(() -> new NotFoundException("RendezVous not found with id: " + rendezVous.getId()));
        existingRendezVous.setDate(rendezVous.getDate());
        existingRendezVous.setHeure(rendezVous.getHeure());
        existingRendezVous.setEstFait(rendezVous.isEstFait());
        existingRendezVous.setEstInscrit(rendezVous.isEstInscrit());
        existingRendezVous.setModification(true);
        existingRendezVous.setDateModification(LocalDateTime.now());
        existingRendezVous.setAnnule(rendezVous.isAnnule());
        existingRendezVous.setDateAnnulation(rendezVous.getDateAnnulation());
        existingRendezVous.setLead(rendezVous.getLead());
        existingRendezVous.setAdministrateur(rendezVous.getAdministrateur());
        return rendezVousRepository.save(existingRendezVous);
    }

    @Override
    public void delete(int id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                                                     .orElseThrow(() -> new NotFoundException("RendezVous not found with id: " + id));
        rendezVousRepository.delete(rendezVous);
    }
    
    public RendezVous updateDateTime(int id, LocalDate date, LocalTime heure) {
        RendezVous existingRendezVous = findById(id);
        existingRendezVous.setDate(date);
        existingRendezVous.setHeure(heure);
        existingRendezVous.setModification(true);
        existingRendezVous.setDateModification(LocalDateTime.now());
        return rendezVousRepository.save(existingRendezVous);
    }
    
    public RendezVous cancelRendezVous(int id) {
        RendezVous existingRendezVous = findById(id);
        existingRendezVous.setAnnule(true);
        existingRendezVous.setDateAnnulation(LocalDateTime.now());
        return rendezVousRepository.save(existingRendezVous);
    }

    public RendezVous markFait(int id) {
        RendezVous existingRendezVous = findById(id);
        existingRendezVous.setEstFait(true);
        existingRendezVous.setDateFait(LocalDateTime.now());
        existingRendezVous.setAnnule(true);
        existingRendezVous.setDateAnnulation(LocalDateTime.now());
        return rendezVousRepository.save(existingRendezVous);
    }

    
    public RendezVous markInscription(int id, boolean estInscrit) {
        RendezVous existingRendezVous = findById(id);
        existingRendezVous.setEstFait(true);
        existingRendezVous.setEstInscrit(estInscrit);
        existingRendezVous.setDateFait(LocalDateTime.now());
        return rendezVousRepository.save(existingRendezVous);
    }
    
    public List<RendezVous> findFaitByDate(LocalDate date) {
        return rendezVousRepository.findByDateAndEstFait(date, true);
    }

    public List<RendezVous> findByDate(LocalDate date ){
        return rendezVousRepository.findByDate(date);
    }
    
    public List<RendezVous> findNonFaitByDate(LocalDate date) {
        return rendezVousRepository.findByDateAndEstFait(date, false);
    }
    
    public List<RendezVous> findAllNonFait() {
        return rendezVousRepository.findByEstFait(false);
    }
    
    public List<RendezVous> findAnnule() {
        return rendezVousRepository.findByAnnuleTrue();
    }
    
    public List<RendezVous> findModified() {
        return rendezVousRepository.findByModification(true);
    }

    public void test(){
        List<RendezVous> rendezVous = findAll();
        for(RendezVous r : rendezVous){
            r.setDate(LocalDate.now());
            rendezVousRepository.save(r);
        }
    }
}
