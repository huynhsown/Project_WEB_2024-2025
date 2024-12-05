package com.adidark.service.impl;

import com.adidark.converter.PaymentDTOConverter;
import com.adidark.entity.OrderEntity;
import com.adidark.entity.PaymentEntity;
import com.adidark.exception.DataNotFoundException;
import com.adidark.model.dto.PaymentDTO;
import com.adidark.repository.OrderRepository;
import com.adidark.repository.PaymentRepository;
import com.adidark.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentDTOConverter paymentDTOConverter;

    @Override
    public PaymentEntity createPayment(Long orderId, PaymentDTO paymentDTO) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        PaymentEntity paymentEntity = paymentDTOConverter.toPaymentEntity(paymentDTO);
        paymentEntity.setOrderEntity(orderEntity);
        return paymentRepository.save(paymentEntity);
    }
}
