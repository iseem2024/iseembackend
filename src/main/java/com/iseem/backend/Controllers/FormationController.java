package com.iseem.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iseem.backend.DTO.FormationDTO;
import com.iseem.backend.Entities.Formation;
import com.iseem.backend.Services.FormationService;

import java.util.List;

@RestController
@RequestMapping("/api/v0/formations")
public class FormationController {

    @Autowired
    private FormationService formationService;

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable int id) {
        Formation formation = formationService.findById(id);
        return ResponseEntity.ok(formation);
    }

    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        List<Formation> formations = formationService.findAll();
        return ResponseEntity.ok(formations);
    }

    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        Formation createdFormation = formationService.create(formation);
        return ResponseEntity.ok(createdFormation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable int id, @RequestBody Formation formation) {
        Formation updatedFormation = formationService.update(formation);
        return ResponseEntity.ok(updatedFormation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable int id) {
        formationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<List<FormationDTO>> findformationWithLeadsCount(){
        List<FormationDTO> dtos = formationService.findformationWithLeadsCount();
        return ResponseEntity.ok(dtos);
    }
}
