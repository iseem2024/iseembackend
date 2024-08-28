package com.iseem.backend.Services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Entities.RendezVous;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Exceptions.RendezVousExistsException;
import com.iseem.backend.Repositories.UserRepository;
import com.iseem.backend.Repositories.RendezVousRepository;
import com.iseem.backend.dao.IDao;

@Service
public class RendezVousService implements IDao<RendezVous> {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private LeadService leadService;

    @Autowired
    private UserRepository administrateurRepository;

    @Autowired
    private UserService userService;

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
        administrateurRepository.findById(rendezVous.getUser().getId())
                .orElseThrow(() -> new NotFoundException(
                        "Administrator not found with id : " + rendezVous.getUser().getId()));
        RendezVous existingRendezVous = rendezVousRepository.findByLeadAndAnnuleFalse(lead);

        if (existingRendezVous != null) {
            throw new RendezVousExistsException("This lead already has a appointment: " + existingRendezVous.getId(),
                    existingRendezVous);
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
        if (rendezVous == null)
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
        existingRendezVous.setUser(rendezVous.getUser());
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

    public List<RendezVous> findByDate(LocalDate date) {
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

    public void test() {
        List<RendezVous> rendezVous = findAll();
        for (RendezVous r : rendezVous) {
            r.setDate(LocalDate.now());
            rendezVousRepository.save(r);
        }
    }

    public Page<RendezVous> getRendezVousUserByDay(int userId, LocalDate date, int page, int size) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);

        User user = userService.findById(userId);
        return rendezVousRepository.findByUserAndDateCreationBetween(user, startOfDay, endOfDay, pageable);

    }

    public Page<RendezVous> getRendezVousUserByMonth(int userId, YearMonth yearMonth, int page, int size) {
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);

        User user = userService.findById(userId);
        return rendezVousRepository.findByUserAndDateCreationBetween(user, startOfMonth, endOfMonth, pageable);

    }

    public Page<RendezVous> getRendezVousUserByYear(int userId, Year year, int page, int size) {
        LocalDateTime startOfYear = year.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = year.atMonth(12).atEndOfMonth().atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);

        User user = userService.findById(userId);
        return rendezVousRepository.findByUserAndDateCreationBetween(user, startOfYear, endOfYear, pageable);
    }

    public Page<RendezVous> getRendezVousUserByCurrentWeek(int userId, int page, int size) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atStartOfDay();
        Pageable pageable = PageRequest.of(page, size);
        User user = userService.findById(userId);
        return rendezVousRepository.findByUserAndDateCreationBetween(user, startOfWeek, endOfWeek, pageable);
    }

    public void changeUser() {
        List<RendezVous> rdvs = findAll();
        User u1 = userService.findById(18);
        User u2 = userService.findById(19);
        int i = 0;
        for (RendezVous rdv : rdvs) {
            if (i % 2 == 0) {
                rdv.setUser(u1);
                rendezVousRepository.save(rdv);
            } else {
                rdv.setUser(u2);
                rendezVousRepository.save(rdv);
            }
            i++;
        }
    }

    public List<RendezVous> getRendezVousByDay(LocalDate date) {
        return rendezVousRepository.findRendezVoussByDateBetween(date, date);
    }

    public List<RendezVous> getRendezVousByMonth(YearMonth yearMonth) {
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        return rendezVousRepository.findRendezVoussByDateBetween(startOfMonth, endOfMonth);
    }

    public List<RendezVous> getRendezVousByYear(Year year) {
        LocalDate startOfYear = year.atDay(1);
        LocalDate endOfYear = year.atMonth(12).atEndOfMonth();
        return rendezVousRepository.findRendezVoussByDateBetween(startOfYear, endOfYear);
    }

    public List<RendezVous> getRendezVousByCurrentWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return rendezVousRepository.findRendezVoussByDateBetween(startOfWeek, endOfWeek);
    }
}
