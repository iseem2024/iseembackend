package com.iseem.backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iseem.backend.Entities.Administrateur;
import com.iseem.backend.Services.AdministrateurService;

@RestController
@RequestMapping("/api/v0/administrateurs")
public class AdministrateurController {

    @Autowired
    private AdministrateurService administrateurService;

    @GetMapping("/{id}")
    public ResponseEntity<Administrateur> getAdministrateurById(@PathVariable int id) {
        Administrateur administrateur = administrateurService.findById(id);
        return ResponseEntity.ok(administrateur);
    }

    @GetMapping
    public ResponseEntity<List<Administrateur>> getAllAdministrateurs() {
        List<Administrateur> administrateurs = administrateurService.findAll();
        return ResponseEntity.ok(administrateurs);
    }

    @PostMapping
    public ResponseEntity<Administrateur> createAdministrateur(@RequestBody Administrateur administrateur) {
        Administrateur createdAdministrateur = administrateurService.create(administrateur);
        return ResponseEntity.ok(createdAdministrateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrateur> updateAdministrateur(@PathVariable int id, @RequestBody Administrateur administrateur) {
        administrateur.setId(id);
        Administrateur updatedAdministrateur = administrateurService.update(administrateur);
        return ResponseEntity.ok(updatedAdministrateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrateur(@PathVariable int id) {
        administrateurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyPasswordHash(@RequestParam String pwd, @RequestParam String hash){
        return ResponseEntity.ok(administrateurService.verifyPasswordHash(pwd,hash));
    }
}
