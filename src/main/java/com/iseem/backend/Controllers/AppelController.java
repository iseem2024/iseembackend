package com.iseem.backend.Controllers;

import com.iseem.backend.DTO.AppelDTO;
import com.iseem.backend.Entities.Appel;
import com.iseem.backend.Services.AppelService;
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

    @GetMapping("/day/{userId}")
    public Page<AppelDTO> getAppelsByDay(@PathVariable("userId") int userId,
                                      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return appelService.getAppelsByDay(userId, date, page, size);
    }

    @GetMapping("/month/{userId}")
    public Page<AppelDTO> getAppelsByMonth(@PathVariable("userId") int userId,
                                        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return appelService.getAppelsByMonth(userId, yearMonth, page, size);
    }

    @GetMapping("/year/{userId}")
    public Page<AppelDTO> getAppelsByYear(@PathVariable("userId") int userId,
                                       @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Year year,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return appelService.getAppelsByYear(userId, year, page, size);
    }

    @GetMapping("/week/{userId}")
    public Page<AppelDTO> getAppelsByWeek(@PathVariable("userId") int userId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return appelService.getAppelsByCurrentWeek(userId, page, size);
    }

    @PostMapping("/changeUser")
    public void changeUser(){
        appelService.changeUser();
    }
}
