package com.iseem.backend.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Client;
import com.iseem.backend.Entities.Inscription;
import com.iseem.backend.Entities.Lead;
import com.iseem.backend.Entities.Payment;
import com.iseem.backend.Exceptions.AlreadyExistException;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.ClientRepository;
import com.iseem.backend.dao.IDao;
import org.springframework.context.annotation.Lazy;

@Service
public class ClientService implements IDao<Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    @Lazy
    private InscriptionService inscriptionService;

    @Autowired
    @Lazy
    private LeadService LeadService;

    @Override
    public Client findById(int id) {
        Client client = clientRepository.findById(id)
                                        .orElseThrow(() -> new NotFoundException("Client not found with id : " + id));
        return client;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = clientRepository.findAll();
        return clients;
    }

    @Override
    public Client create(Client client) {
        Client c = clientRepository.findByTelephone(client.getTelephone());
        if (c!= null) {
            throw new AlreadyExistException("Client with this telephone already exists");
        }
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        Client existingClient = clientRepository.findById(client.getId())
                                                .orElseThrow(() -> new NotFoundException("Client not found with id : " + client.getId()));

        existingClient.setNom(client.getNom());
        existingClient.setPrenom(client.getPrenom());
        existingClient.setEmail(client.getEmail());
        existingClient.setTelephone(client.getTelephone());
        existingClient.setWhatsapp(client.getWhatsapp());
        existingClient.setNiveau(client.getNiveau());
        existingClient.setCin(client.getCin());
        existingClient.setSexe(client.getSexe());

        return clientRepository.save(existingClient);
    }

    @Override
    public void delete(int id) {
        Client client = clientRepository.findById(id)
                                        .orElseThrow(() -> new NotFoundException("Client not found with id : " + id));
        clientRepository.delete(client);
    }

    public Inscription inscrClientNotExist(Client client, int formationId, int leadId, int duree, String uniteDuree, float prixTotal, float prixInscription) {
        if(leadId != 0){
            Lead lead = LeadService.findById(leadId);
            client.setLead(lead);
        }
        Client savedClient = create(client);
        Inscription inscription = inscriptionService.GenerateInsc(savedClient.getId(), formationId, duree, uniteDuree, prixTotal, prixInscription);
        if(prixInscription != 0){
            Payment payment = new Payment();
            payment.setMontant(prixInscription);
            payment.setType("Espèces");
            inscriptionService.AddPayment(savedClient.getId(), formationId, payment);
        }
        return inscription;

    }

    public Inscription inscClientExist(int clientId, int formationId, Client client, int duree, String uniteDuree, float prixTotal, float prixInscription) {
        client.setId(clientId);
        update(client);
        Inscription inscription = inscriptionService.GenerateInsc(clientId, formationId, duree, uniteDuree, prixTotal, prixInscription);
        if(prixInscription != 0){
            Payment payment = new Payment();
            payment.setMontant(prixInscription);
            payment.setType("Espèces");
            inscriptionService.AddPayment(clientId, formationId, payment);
        }
        return inscription;
    }

    public Client searClientBytelephone(String telephone){
        return clientRepository.findByTelephone(telephone);
    }


}
