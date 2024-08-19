package com.iseem.backend.Controllers;

import com.iseem.backend.DTO.PaymentDTO;
import com.iseem.backend.Entities.Payment;
import com.iseem.backend.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v0/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        Payment payment = paymentService.findById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.findAll();
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment newPayment = paymentService.create(payment);
        return ResponseEntity.ok(newPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody Payment paymentDetails) {
        Payment updatedPayment = paymentService.update(paymentDetails);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day")
    public List<PaymentDTO> getPaymentsByDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return paymentService.getPaymentsByDay(date);
    }

    @GetMapping("/month")
    public List<PaymentDTO> getPaymentsByMonth(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        return paymentService.getPaymentsByMonth(yearMonth);
    }

    @GetMapping("/year")
    public List<PaymentDTO> getPaymentsByYear(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Year year) {
        return paymentService.getPaymentsByYear(year);
    }
}
