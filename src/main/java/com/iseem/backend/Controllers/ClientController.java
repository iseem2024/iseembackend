package com.iseem.backend.Controllers;

import com.iseem.backend.Entities.Client;
import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.create(client);
        return ResponseEntity.ok(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable int id, @RequestBody Client client) {
        client.setId(id);  // Set the id to ensure the correct client is updated
        Client updatedClient = clientService.update(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/inscription/notexist/{formationId}")
    public ResponseEntity<Inscription> inscrClientNotExist(@RequestBody Client client, @PathVariable int formationId, @RequestParam int leadId){        
        Inscription inscription = clientService.inscrClientNotExist(client, formationId,leadId);
        return ResponseEntity.ok(inscription);
    }
    @PostMapping("/inscription/exist/{clientId}/{formationId}")
    public ResponseEntity<Inscription> inscrClientExist(@PathVariable int clientId, @PathVariable int formationId){
        Inscription inscription = clientService.inscClientExist(clientId, formationId);
        return ResponseEntity.ok(inscription);
    }
    @GetMapping("/search/{telephone}")
    public ResponseEntity<Client> searchClient(@PathVariable String telephone){
        Client client = clientService.searClientBytelephone(telephone);
        return ResponseEntity.ok(client);
    }
}
