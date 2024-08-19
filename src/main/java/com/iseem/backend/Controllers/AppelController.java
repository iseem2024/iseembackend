package com.iseem.backend.Controllers;

import com.iseem.backend.Entities.Appel;
import com.iseem.backend.Services.AppelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/appels")
public class AppelController {

    @Autowired
    private AppelService appelService;

    @GetMapping("/{id}")
    public ResponseEntity<Appel> getAppelById(@PathVariable int id) {
        Appel appel = appelService.findById(id);
        return ResponseEntity.ok(appel);
    }

    @GetMapping
    public ResponseEntity<List<Appel>> getAllAppels() {
        List<Appel> appels = appelService.findAll();
        return ResponseEntity.ok(appels);
    }

    @PostMapping("/{leadId}/{formationId}")
    public ResponseEntity<Appel> createAppel(@PathVariable int leadId, @PathVariable int formationId, @RequestBody Appel appel) {
        Appel newAppel = appelService.addAppel(appel, leadId, formationId);
        return ResponseEntity.ok(newAppel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appel> updateAppel(@PathVariable int id, @RequestBody Appel appelDetails) {
        Appel updatedAppel = appelService.update(appelDetails);
        return ResponseEntity.ok(updatedAppel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppel(@PathVariable int id) {
        appelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
