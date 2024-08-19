package com.iseem.backend.Controllers;

import com.iseem.backend.Entities.Depense;
import com.iseem.backend.Services.DepenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v0/depenses")
public class DepenseController {

    @Autowired
    private DepenseService depenseService;

    @GetMapping("/{id}")
    public ResponseEntity<Depense> getDepenseById(@PathVariable int id) {
        Depense depense = depenseService.findById(id);
        return ResponseEntity.ok(depense);
    }

    @GetMapping
    public ResponseEntity<List<Depense>> getAllDepenses() {
        List<Depense> depenses = depenseService.findAll();
        return ResponseEntity.ok(depenses);
    }

    @PostMapping
    public ResponseEntity<Depense> createDepense(@RequestBody Depense depense) {
        Depense newDepense = depenseService.create(depense);
        return ResponseEntity.ok(newDepense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Depense> updateDepense(@PathVariable int id, @RequestBody Depense depenseDetails) {
        Depense updatedDepense = depenseService.update(depenseDetails);
        return ResponseEntity.ok(updatedDepense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepense(@PathVariable int id) {
        depenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day")
    public List<Depense> getDepensesByDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return depenseService.getDepensesByDay(date);
    }

    @GetMapping("/month")
    public List<Depense> getDepensesByMonth(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        return depenseService.getDepensesByMonth(yearMonth);
    }

    @GetMapping("/year")
    public List<Depense> getDepensesByYear(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Year year) {
        return depenseService.getDepensesByYear(year);
    }
}
