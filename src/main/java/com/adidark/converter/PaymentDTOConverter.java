package com.adidark.converter;

import com.adidark.entity.PaymentEntity;
import com.adidark.model.dto.PaymentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PaymentEntity toPaymentEntity(PaymentDTO paymentDTO){
        return modelMapper.map(paymentDTO, PaymentEntity.class);
    }
}
