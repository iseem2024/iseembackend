package com.iseem.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
    Client findByTelephone(String telephone);
}
