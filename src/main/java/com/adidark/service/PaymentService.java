package com.adidark.service;

import com.adidark.entity.PaymentEntity;
import com.adidark.model.dto.PaymentDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface PaymentService {

    PaymentEntity createPayment(Long orderId, PaymentDTO paymentDTO);

}
