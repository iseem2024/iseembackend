package com.iseem.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iseem.backend.DTO.LeadDTO;
import com.iseem.backend.DTO.LeadDTO;
import com.iseem.backend.DTO.LeadDuplicationDTO;
import com.iseem.backend.DTO.LeadsFormationDTO;
import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Services.LeadService;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v0/leads")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @GetMapping("/{id}")
    public ResponseEntity<Lead> getLeadById(@PathVariable int id) {
        Lead lead = leadService.findById(id);
        return ResponseEntity.ok(lead);
    }

    @GetMapping
    public ResponseEntity<List<Lead>> getAllLeads() {
        List<Lead> leads = leadService.findAll();
        return ResponseEntity.ok(leads);
    }

    @PostMapping
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
        Lead createdLead = leadService.create(lead);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLead);
    }

    @PostMapping("/{formationId}")
    public ResponseEntity<LeadDuplicationDTO> createLeaads(@PathVariable int formationId, @RequestBody List<Lead> leads) {
        LeadDuplicationDTO newLeads = leadService.createLeads(leads, formationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLeads);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lead> updateLead(@PathVariable int id, @RequestBody Lead lead) {
        lead.setId(id);
        Lead updatedLead = leadService.update(lead);
        return ResponseEntity.ok(updatedLead);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable int id) {
        leadService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/markInterested")
    public ResponseEntity<Lead> markLeadInterested(@PathVariable int id) {
        Lead markedLead = leadService.markLeadInterested(id);
        return ResponseEntity.ok(markedLead);
    }

    @PutMapping("/{id}/markCalled")
    public ResponseEntity<Lead> markLeadCalled(@PathVariable int id) {
        Lead markedLead = leadService.markLeadCalled(id);
        return ResponseEntity.ok(markedLead);
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<Lead>> getLeadsByDate(@PathVariable("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<Lead> leads = leadService.findLeadsByDate(parsedDate);
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/uncontacted-by-date/{date}")
    public ResponseEntity<List<Lead>> getUncontactedLeadsByDate(@PathVariable("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<Lead> leads = leadService.findUncontactedLeadsByDate(parsedDate);
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/uncontacted-order-by-import-date")
    public ResponseEntity<List<Lead>> getAllUncontactedLeadsOrderByDateDesc() {
        List<Lead> leads = leadService.findAllUncontactedLeadsOrderByDateDesc();
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/formation/{formationId}")
    public ResponseEntity<LeadsFormationDTO> finByInformationId(
            @PathVariable int formationId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        LeadsFormationDTO leads = leadService.findLeadsByinformationId(formationId, search, search, page, size);
        // List<Lead> leads = leadService.findLeadsByinformationId(formationId);
        return ResponseEntity.ok(leads);
    }

    // @GetMapping("/number")
    // public void test () {
    // List<Lead> leads = leadService.findAll();
    // for(Lead lead : leads){
    // lead.setTelephone("+212" + lead.getTelephone().substring(1));
    // leadService.create(lead);
    // }
    // }

    @GetMapping("/search/{telephone}")
    public ResponseEntity<Lead> searchLead(@PathVariable String telephone) {
        Lead lead = leadService.searchLead(telephone);
        return ResponseEntity.ok(lead);
    }

     @GetMapping("/day/{userId}")
    public Page<LeadDTO> getLeadsByDay(@PathVariable("userId") int userId,
                                      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return leadService.getLeadsByDay(userId, date, page, size);
    }

    @GetMapping("/month/{userId}")
    public Page<LeadDTO> getLeadsByMonth(@PathVariable("userId") int userId,
                                        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return leadService.getLeadsByMonth(userId, yearMonth, page, size);
    }

    @GetMapping("/year/{userId}")
    public Page<LeadDTO> getLeadsByYear(@PathVariable("userId") int userId,
                                       @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Year year,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return leadService.getLeadsByYear(userId, year, page, size);
    }

    @GetMapping("/week/{userId}")
    public Page<LeadDTO> getLeadsByWeek(@PathVariable("userId") int userId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return leadService.getLeadsByCurrentWeek(userId, page, size);
    }
}
