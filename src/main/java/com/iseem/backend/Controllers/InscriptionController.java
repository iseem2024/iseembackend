package com.iseem.backend.Controllers;

import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.Payment;
import com.iseem.backend.Services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

import java.util.List;

@RestController
@RequestMapping("/api/v0/inscriptions")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    @GetMapping("/{clientId}/{formationId}")
    public ResponseEntity<Inscription> getInscriptionById(@PathVariable int clientId, @PathVariable int formationId) {
        Inscription inscription = inscriptionService.findById(clientId, formationId);
        return ResponseEntity.ok(inscription);
    }

    @GetMapping
    public ResponseEntity<List<Inscription>> getAllInscriptions() {
        List<Inscription> inscriptions = inscriptionService.findAll();
        return ResponseEntity.ok(inscriptions);
    }

    @PostMapping
    public ResponseEntity<Inscription> createInscription(@RequestBody Inscription inscription) {
        Inscription createdInscription = inscriptionService.create(inscription);
        return ResponseEntity.ok(createdInscription);
    }

    @DeleteMapping("/{clientId}/{formationId}")
    public ResponseEntity<Void> deleteInscription(@PathVariable int clientId, @PathVariable int formationId) {
        inscriptionService.delete(clientId, formationId);
        return ResponseEntity.noContent().build();
    }

    // @PostMapping("/generate")
    // public ResponseEntity<Inscription> generateInscription(@RequestParam int clientId, @RequestParam int formationId) {
    //     Inscription inscription = inscriptionService.GenerateInsc(clientId, formationId);
    //     return ResponseEntity.ok(inscription);
    // }

    @PostMapping("/{clientId}/{formationId}/payments")
    public ResponseEntity<Payment> addPayment(@PathVariable int clientId, @PathVariable int formationId, @RequestBody Payment payment) {
        Payment savedPayment = inscriptionService.AddPayment(clientId, formationId, payment);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping("/formation/{formationId}")
    public ResponseEntity<List<Inscription>> getClientByFormation(@PathVariable int formationId){
        List<Inscription> inscriptions = inscriptionService.getClientByFormation(formationId);
        return ResponseEntity.ok(inscriptions);
    }

    @GetMapping("/day/{userId}")
    public Page<Inscription> getInscriptionsByDay(@PathVariable("userId") int userId,
                                                  @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return inscriptionService.getInscriptionsByDay(userId, date, page, size);
    }

    @GetMapping("/month/{userId}")
    public Page<Inscription> getInscriptionsByMonth(@PathVariable("userId") int userId,
                                                    @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return inscriptionService.getInscriptionsByMonth(userId, yearMonth, page, size);
    }

    @GetMapping("/year/{userId}")
    public Page<Inscription> getInscriptionsByYear(@PathVariable("userId") int userId,
                                                   @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Year year,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return inscriptionService.getInscriptionsByYear(userId, year, page, size);
    }

    @GetMapping("/week/{userId}")
    public Page<Inscription> getInscriptionsByWeek(@PathVariable("userId") int userId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return inscriptionService.getInscriptionsByCurrentWeek(userId, page, size);
    }
    @PostMapping("/changeUser")
    public void changeUser(){
        inscriptionService.changeUser();
    }
}
