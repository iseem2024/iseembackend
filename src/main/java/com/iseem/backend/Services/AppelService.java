package com.iseem.backend.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Appel;
import com.iseem.backend.Entities.Formation;
import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.AppelRepository;
import com.iseem.backend.dao.IDao;

@Service
public class AppelService implements IDao<Appel> {

    @Autowired
    private AppelRepository appelRepository;
    @Autowired
    private LeadService leadService;
    @Autowired
    private FormationService formationService;

    @Override
    public Appel findById(int id) {
        return appelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appel not found with id : " + id));
    }

    @Override
    public List<Appel> findAll() {
        return appelRepository.findAll();
    }

    @Override
    public Appel create(Appel o) {
        // leadService.findById(o.getLead().getId());
        o.setDate(LocalDateTime.now());
        return appelRepository.save(o);
    }

    public Appel addAppel(Appel o, int leadId, int formatioId){
        Lead lead = leadService.findById(leadId);
        Formation formation = formationService.findById(formatioId);
        o.setLead(lead);
        o.setFormation(formation);
        o.setDate(LocalDateTime.now());
        return appelRepository.save(o);
    }

    @Override
    public Appel update(Appel o) {
        Appel existingAppel = appelRepository.findById(o.getId())
                .orElseThrow(() -> new NotFoundException("Appel not found with id : " + o.getId()));
        existingAppel.setStatut(o.getStatut());
        existingAppel.setContenu(o.getContenu());
        return appelRepository.save(existingAppel);
    }

    @Override
    public void delete(int id) {
        Appel existingAppel = appelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appel not found with id : " + id));
        appelRepository.delete(existingAppel);
    }

}
