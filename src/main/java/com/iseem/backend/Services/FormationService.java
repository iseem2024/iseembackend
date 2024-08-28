package com.iseem.backend.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.DTO.FormationDTO;
import com.iseem.backend.Entities.Formation;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.FormationRepository;
import com.iseem.backend.dao.IDao;

@Service
public class FormationService implements IDao<Formation> {

    @Autowired
    private FormationRepository formationRepository;

    @Override
    public Formation findById(int id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Formation not found with id : " + id));
        return formation;
    }

    @Override
    public List<Formation> findAll() {
        List<Formation> formations = formationRepository.findAll();
        return formations;
    }

    @Override
    public Formation create(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Formation update(Formation formation) {
        Formation existingFormation = formationRepository.findById(formation.getId())
                .orElseThrow(() -> new NotFoundException("Formation not found with id : " + formation.getId()));

        existingFormation.setNom(formation.getNom());
        existingFormation.setDateDebut(formation.getDateDebut());
        existingFormation.setDateFin(formation.getDateFin());
        existingFormation.setDuree(formation.getDuree());
        existingFormation.setCondition(formation.getCondition());
        existingFormation.setType(formation.getType());
        return formationRepository.save(existingFormation);
    }

    @Override
    public void delete(int id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Formation not found with id : " + id));
        formationRepository.delete(formation);
    }

    public List<FormationDTO> findformationWithLeadsCount() {
        return formationRepository.findformationWithLeadsCount();
    }
}
