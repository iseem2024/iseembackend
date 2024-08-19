package com.iseem.backend.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.DTO.PaymentDTO;
import com.iseem.backend.Entities.Payment;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.PaymentRepository;
import com.iseem.backend.dao.IDao;

@Service
public class PaymentService implements IDao<Payment> {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment findById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id : " + id));
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment create(Payment o) {
        o.setDatePaiement(LocalDateTime.now());
        return paymentRepository.save(o);
    }

    @Override
    public Payment update(Payment o) {
        Payment existingPayment = paymentRepository.findById(o.getId())
                .orElseThrow(() -> new NotFoundException("Payment not found with id : " + o.getId()));
        existingPayment.setMontant(o.getMontant());
        existingPayment.setType(o.getType());
        return paymentRepository.save(existingPayment);
    }

    @Override
    public void delete(int id) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id : " + id));
        paymentRepository.delete(existingPayment);
    }

    public List<PaymentDTO> getPaymentsByDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return paymentRepository.findPaymentsByDatePaiementBetween(startOfDay, endOfDay)
                .stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());
    }

    // Rechercher les paiements par mois
    public List<PaymentDTO> getPaymentsByMonth(YearMonth yearMonth) {
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return paymentRepository.findPaymentsByDatePaiementBetween(startOfMonth, endOfMonth)
                .stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());
    }

    // Rechercher les paiements par année
    public List<PaymentDTO> getPaymentsByYear(Year year) {
        LocalDateTime startOfYear = year.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = year.atMonth(12).atEndOfMonth().atTime(23, 59, 59);
        return paymentRepository.findPaymentsByDatePaiementBetween(startOfYear, endOfYear)
                .stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());
    }
}
