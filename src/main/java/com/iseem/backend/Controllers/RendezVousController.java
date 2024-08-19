package com.iseem.backend.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iseem.backend.Entities.RendezVous;
import com.iseem.backend.Services.RendezVousService;

@RestController
@RequestMapping("/api/v0/rendezvous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @GetMapping("/{id}")
    public ResponseEntity<RendezVous> getRendezVousById(@PathVariable int id) {
        RendezVous rendezVous = rendezVousService.findById(id);
        return ResponseEntity.ok().body(rendezVous);
    }

    @GetMapping
    public ResponseEntity<List<RendezVous>> getAllRendezVous(){
        List<RendezVous> rendezVous= rendezVousService.findAll();
        return ResponseEntity.ok(rendezVous);
    }

    @PostMapping
    public ResponseEntity<RendezVous> createRendezVous(@RequestBody RendezVous rendezVous) {
        RendezVous createdRendezVous = rendezVousService.create(rendezVous);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRendezVous);
    }

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<Object> getRendezVousByLeadId(@PathVariable int leadId) {
        Object object = rendezVousService.getByLeadId(leadId);
        return ResponseEntity.ok().body(object);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<RendezVous> updateRendezVous(@PathVariable int id, @RequestBody RendezVous rendezVous) {
        rendezVous.setId(id);
        RendezVous updatedRendezVous = rendezVousService.update(rendezVous);
        return ResponseEntity.ok().body(updatedRendezVous);
    }

    @PutMapping("/updateDateTime/{id}")
    public ResponseEntity<RendezVous> updateDateTime(@PathVariable int id,
                                                     @RequestParam LocalDate date,
                                                     @RequestParam LocalTime heure) {
        RendezVous updatedRendezVous = rendezVousService.updateDateTime(id, date, heure);
        return ResponseEntity.ok().body(updatedRendezVous);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<RendezVous> cancelRendezVous(@PathVariable int id) {
        RendezVous cancelledRendezVous = rendezVousService.cancelRendezVous(id);
        return ResponseEntity.ok().body(cancelledRendezVous);
    }

    @PutMapping("/markfait/{id}")
    public ResponseEntity<RendezVous> markFait(@PathVariable int id){
        RendezVous updatedRendezVous = rendezVousService.markFait(id);
        return ResponseEntity.ok().body(updatedRendezVous);
    }

    @PutMapping("/markInscription/{id}")
    public ResponseEntity<RendezVous> markInscription(@PathVariable int id,
                                                       @RequestParam boolean estInscrit) {
        RendezVous updatedRendezVous = rendezVousService.markInscription(id, estInscrit);
        return ResponseEntity.ok().body(updatedRendezVous);
    }

    @GetMapping("/fait")
    public ResponseEntity<List<RendezVous>> getFaitByDate(@RequestParam LocalDate date) {
        List<RendezVous> rendezVousList = rendezVousService.findFaitByDate(date);
        return ResponseEntity.ok().body(rendezVousList);
    }

    @GetMapping("/nonfait")
    public ResponseEntity<List<RendezVous>> getNonFaitByDate(@RequestParam LocalDate date) {
        List<RendezVous> rendezVousList = rendezVousService.findNonFaitByDate(date);
        return ResponseEntity.ok().body(rendezVousList);
    }

    @GetMapping("/tousnonfait")
    public ResponseEntity<List<RendezVous>> getAllNonFait() {
        List<RendezVous> rendezVousList = rendezVousService.findAllNonFait();
        return ResponseEntity.ok().body(rendezVousList);
    }

    @GetMapping("/cancel")
    public ResponseEntity<List<RendezVous>> getAnnuleByDate() {
        List<RendezVous> rendezVousList = rendezVousService.findAnnule();
        return ResponseEntity.ok().body(rendezVousList);
    }

    @GetMapping("/modified")
    public ResponseEntity<List<RendezVous>> getModified() {
        List<RendezVous> rendezVousList = rendezVousService.findModified();
        return ResponseEntity.ok().body(rendezVousList);
    }

    @GetMapping("/today")
    public ResponseEntity<List<RendezVous>> getTodayRendezVous() {
        LocalDate today = LocalDate.now();
        List<RendezVous> rendezVousList = rendezVousService.findByDate(today);
        return ResponseEntity.ok().body(rendezVousList);
    }
    @PostMapping("/test")
    public void test(){
        rendezVousService.test();
    }
}
