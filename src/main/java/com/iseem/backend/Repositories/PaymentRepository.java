package com.iseem.backend.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
     List<Payment> findPaymentsByDatePaiementBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
