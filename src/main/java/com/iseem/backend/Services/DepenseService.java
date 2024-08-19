package com.iseem.backend.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Depense;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.DepenseRepository;

@Service
public class DepenseService {

    @Autowired
    private DepenseRepository depenseRepository;

    public Depense findById(int id) {
        return depenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Depense not found with id : " + id));
    }

    public List<Depense> findAll() {
        return depenseRepository.findAll();
    }

    public Depense create(Depense depense) {
        depense.setDateDepense(LocalDateTime.now());
        return depenseRepository.save(depense);
    }

    public Depense update(Depense depense) {
        Depense existingDepense = depenseRepository.findById(depense.getId())
                .orElseThrow(() -> new NotFoundException("Depense not found with id : " + depense.getId()));
        existingDepense.setMontant(depense.getMontant());
        existingDepense.setType(depense.getType());
        existingDepense.setDescription(depense.getDescription());
        return depenseRepository.save(existingDepense);
    }

    public void delete(int id) {
        Depense existingDepense = depenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Depense not found with id : " + id));
        depenseRepository.delete(existingDepense);
    }

    public List<Depense> getDepensesByDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return depenseRepository.findDepensesByDateDepenseBetween(startOfDay, endOfDay);
    }

    public List<Depense> getDepensesByMonth(YearMonth yearMonth) {
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return depenseRepository.findDepensesByDateDepenseBetween(startOfMonth, endOfMonth);
    }

    public List<Depense> getDepensesByYear(Year year) {
        LocalDateTime startOfYear = year.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = year.atMonth(12).atEndOfMonth().atTime(23, 59, 59);
        return depenseRepository.findDepensesByDateDepenseBetween(startOfYear, endOfYear);
    }
}
