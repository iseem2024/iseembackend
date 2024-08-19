package com.iseem.backend.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Administrateur;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.AdministrateurRepository;
import com.iseem.backend.Utils.PasswordHash;
import com.iseem.backend.dao.IDao;

@Service
public class AdministrateurService implements IDao<Administrateur>{

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Override
    public Administrateur findById(int id) {
        Administrateur administrateur = administrateurRepository.findById(id)
                                        .orElseThrow(() -> new NotFoundException("Administrateur not found with id : " + id));
        return administrateur;                                        
    }

    @Override
    public List<Administrateur> findAll() {
        List<Administrateur> administrateurs = administrateurRepository.findAll();
        return administrateurs; 
    }

    @Override
    public Administrateur create(Administrateur o) {
        o.setMotDePasse(PasswordHash.generateHash(o.getMotDePasse()));
        return administrateurRepository.save(o);
    }

    @Override
    public Administrateur update(Administrateur o) {
        Administrateur administrateur = administrateurRepository.findById(o.getId())
                                        .orElseThrow(() -> new NotFoundException("Administrateur not found with id : " + o.getId()));
        administrateur.setNom(o.getNom());
        administrateur.setEmail(o.getEmail());
        administrateur.setMotDePasse(PasswordHash.generateHash(o.getMotDePasse()));
        return administrateurRepository.save(administrateur);
    }

   

    @Override
    public void delete(int id) {
        Administrateur administrateur = administrateurRepository.findById(id)
                                        .orElseThrow(() -> new NotFoundException("Administrateur not found with id : " + id));
        administrateurRepository.delete(administrateur);
    }
    
    public boolean verifyPasswordHash(String pwd, String hash){
        return PasswordHash.verifyPwd(pwd, hash);
    }
}
