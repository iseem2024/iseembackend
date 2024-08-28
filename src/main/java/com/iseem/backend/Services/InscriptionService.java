package com.iseem.backend.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Appel;
import com.iseem.backend.Entities.Client;
import com.iseem.backend.Entities.Formation;
import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.InscriptionPKA;
import com.iseem.backend.Entities.Payment;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.InscriptionRepository;
import com.iseem.backend.Repositories.PaymentRepository;
import com.iseem.backend.dao.IDao;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

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
    @Autowired
    private UserService userService;

    public Inscription findById(int clientId, int formationId) {
        InscriptionPKA inscriptionPKA = new InscriptionPKA(clientId, formationId);
        Inscription inscription = inscriptionRepository.findById(inscriptionPKA)
                .orElseThrow(() -> new NotFoundException(
                        "Inscription not found with client id : " + clientId + " and formation id : " + formationId));
        return inscription;
    }

    @Override
    public List<Inscription> findAll() {
        List<Inscription> inscriptions = inscriptionRepository.findAllByOrderByDateInscriptionDesc();
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

    public Inscription GenerateInsc(int clientId, int formationId,int duree, String uniteDuree, float prixTotal, float prixInscription){
        Client client = clientService.findById(clientId);
        Formation formation = formationService.findById(formationId);
        InscriptionPKA inscriptionPKA = new InscriptionPKA(clientId, formationId);
        Inscription inscription = new Inscription();
        inscription.setInscriptionPKA(inscriptionPKA);
        inscription.setClient(client);
        inscription.setFormation(formation);
        inscription.setDateInscription(LocalDateTime.now());
        inscription.setDuree(duree);
        inscription.setUniteDuree(uniteDuree);
        inscription.setPrixTotal(prixTotal);
        inscription.setPrixInscription(prixInscription);
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

    public Page<Inscription> getInscriptionsByDay(int userId, LocalDate date, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        return inscriptionRepository.findByUserAndDateInscriptionBetweenOrderByDateInscriptionDesc(user, startOfDay, endOfDay, pageable);
    }

    public Page<Inscription> getInscriptionsByMonth(int userId, YearMonth yearMonth, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        return inscriptionRepository.findByUserAndDateInscriptionBetweenOrderByDateInscriptionDesc(user, startOfMonth, endOfMonth, pageable);
    }

    public Page<Inscription> getInscriptionsByYear(int userId, Year year, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfYear = year.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = year.atMonth(12).atEndOfMonth().atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        return inscriptionRepository.findByUserAndDateInscriptionBetweenOrderByDateInscriptionDesc(user, startOfYear, endOfYear, pageable);
    }

    public Page<Inscription> getInscriptionsByCurrentWeek(int userId, int page, int size) {
        User user = userService.findById(userId);
        LocalDate today = LocalDate.now();
        LocalDateTime startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atStartOfDay();
        Pageable pageable = PageRequest.of(page, size);
        return inscriptionRepository.findByUserAndDateInscriptionBetweenOrderByDateInscriptionDesc(user, startOfWeek, endOfWeek, pageable);
        
    }    public void changeUser(){
        List<Inscription> inscriptions = findAll();
        User u1 = userService.findById(18);
        User u2 = userService.findById(19);
        int i = 0;
        for(Inscription ins : inscriptions){
            if(i % 2 == 0){
                ins.setUser(u1);
                inscriptionRepository.save(ins);
            } else{
                ins.setUser(u2);
                inscriptionRepository.save(ins);
            }
            i++;
        }
    }
    
}
