package com.iseem.backend.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Client;
import com.iseem.backend.Entities.Formation;
import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.InscriptionPKA;
import com.iseem.backend.Entities.Payment;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.InscriptionRepository;
import com.iseem.backend.Repositories.PaymentRepository;
import com.iseem.backend.dao.IDao;

@Service
public class InscriptionService implements IDao<Inscription> {

    @Autowired
    private InscriptionRepository inscriptionRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private FormationService formationService;

    public Inscription findById(int clientId, int formationId) {
        InscriptionPKA inscriptionPKA = new InscriptionPKA(clientId, formationId);
        Inscription inscription = inscriptionRepository.findById(inscriptionPKA)
                .orElseThrow(() -> new NotFoundException(
                        "Inscription not found with client id : " + clientId + " and formation id : " + formationId));
        return inscription;
    }

    @Override
    public List<Inscription> findAll() {
        List<Inscription> inscriptions = inscriptionRepository.findAll();
        return inscriptions;
    }

    @Override
    public Inscription create(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public Inscription update(Inscription inscription) {
        // Pour une clé composite, l'update peut nécessiter une implémentation
        // spécifique
        // Si nécessaire, implémentez la logique de mise à jour appropriée ici
        throw new UnsupportedOperationException("Update operation not supported for composite primary key");
    }

    public void delete(int clientId, int formationId) {
        Inscription inscription = findById(clientId, formationId);
        inscriptionRepository.delete(inscription);
    }

    @Override
    public Inscription findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public Inscription GenerateInsc(int clientId, int formationId){
        Client client = clientService.findById(clientId);
        Formation formation = formationService.findById(formationId);
        InscriptionPKA inscriptionPKA = new InscriptionPKA(clientId, formationId);
        Inscription inscription = new Inscription();
        inscription.setInscriptionPKA(inscriptionPKA);
        inscription.setClient(client);
        inscription.setFormation(formation);
        inscription.setDateInscription(LocalDateTime.now());
        return inscriptionRepository.save(inscription);
    }

    public Payment AddPayment(int clientId, int formationId, Payment payment){
        Inscription inscription = findById(clientId, formationId);
        payment.setDatePaiement(LocalDateTime.now());
        payment.setInscription(inscription);
        return paymentRepository.save(payment);
        
    }

    public List<Inscription> getClientByFormation(int formationId){
        List<Inscription> inscriptions = findAll();
        List<Inscription> filtedInscriptions = new ArrayList<Inscription>();
        for(Inscription inscription : inscriptions){
            if(inscription.getFormation().getId() == formationId){
                filtedInscriptions.add(inscription);
            }
        }
        return filtedInscriptions;
    }
    
}
