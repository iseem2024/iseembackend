package com.iseem.backend.Controllers;

import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.Payment;
import com.iseem.backend.Services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/generate")
    public ResponseEntity<Inscription> generateInscription(@RequestParam int clientId, @RequestParam int formationId) {
        Inscription inscription = inscriptionService.GenerateInsc(clientId, formationId);
        return ResponseEntity.ok(inscription);
    }

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
}
