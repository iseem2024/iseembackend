package com.iseem.backend.DTO;

import com.iseem.backend.Entities.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Payment payment;
    private String formation;
    private String client;
    public PaymentDTO(Payment payment) {
        this.payment = payment;
        this.formation = payment.getInscription().getFormation().getNom();
        this.client = payment.getInscription().getClient().getPrenom()+' '+ payment.getInscription().getClient().getNom();
    }
}
