package com.adidark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    public Long id;
    public BigDecimal amount;
    public String paymentMethod;

    public PaymentDTO(BigDecimal amount, String paymentMethod){
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
}
