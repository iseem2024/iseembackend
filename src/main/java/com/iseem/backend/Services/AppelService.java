package com.iseem.backend.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.temporal.TemporalAdjusters;

import com.iseem.backend.DTO.AppelDTO;
import com.iseem.backend.Entities.Appel;
import com.iseem.backend.Entities.Formation;
import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.AppelRepository;
import com.iseem.backend.dao.IDao;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.DayOfWeek;

@Service
public class AppelService implements IDao<Appel> {

    @Autowired
    private AppelRepository appelRepository;
    @Autowired
    private LeadService leadService;
    @Autowired
    private FormationService formationService;
    @Autowired
    private UserService userService;

    public AppelDTO convertToDTO(Appel appel) {
        return new AppelDTO(
                appel.getId(),
                appel.getStatut(),
                appel.getContenu(),
                appel.getDate(),
                appel.getLead() != null ? appel.getLead().getNomUtilisateur() : null,
                appel.getLead().getTelephone(),
                appel.getFormation() != null ? appel.getFormation().getNom() : null);
    }

    @Override
    public Appel findById(int id) {
        return appelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appel not found with id : " + id));
    }

    @Override
    public List<Appel> findAll() {
        return appelRepository.findAllByOrderByDateDesc();
    }

    @Override
    public Appel create(Appel o) {
        // leadService.findById(o.getLead().getId());
        o.setDate(LocalDateTime.now());
        return appelRepository.save(o);
    }

    public Appel addAppel(Appel o, int leadId, int formatioId) {
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

    public Page<AppelDTO> getAppelsByDay(int userId, LocalDate date, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        Page<Appel> appelsPage = appelRepository.findByUserAndDateBetweenOrderByDateDesc(user, startOfDay, endOfDay,
                pageable);
        return appelsPage.map(this::convertToDTO);
    }

    public Page<AppelDTO> getAppelsByMonth(int userId, YearMonth yearMonth, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        Page<Appel> appelsPage = appelRepository.findByUserAndDateBetweenOrderByDateDesc(user, startOfMonth, endOfMonth,
                pageable);
        return appelsPage.map(this::convertToDTO);
    }

    public Page<AppelDTO> getAppelsByYear(int userId, Year year, int page, int size) {
        User user = userService.findById(userId);
        LocalDateTime startOfYear = year.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = year.atMonth(12).atEndOfMonth().atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);
        Page<Appel> appelsPage = appelRepository.findByUserAndDateBetweenOrderByDateDesc(user, startOfYear, endOfYear,
                pageable);
        return appelsPage.map(this::convertToDTO);
    }

    public Page<AppelDTO> getAppelsByCurrentWeek(int userId, int page, int size) {
        User user = userService.findById(userId);
        LocalDate today = LocalDate.now();
        LocalDateTime startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atStartOfDay();
        Pageable pageable = PageRequest.of(page, size);
        Page<Appel> appelsPage = appelRepository.findByUserAndDateBetweenOrderByDateDesc(user, startOfWeek, endOfWeek,
                pageable);
        return appelsPage.map(this::convertToDTO);
    }

    public void changeUser() {
        List<Appel> appels = findAll();
        User u1 = userService.findById(18);
        User u2 = userService.findById(19);
        int i = 0;
        for (Appel app : appels) {
            if (i % 2 == 0) {
                app.setUser(u1);
                appelRepository.save(app);
            } else {
                app.setUser(u2);
                appelRepository.save(app);
            }
            i++;
        }
    }
}
