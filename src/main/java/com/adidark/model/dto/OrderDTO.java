package com.adidark.model.dto;

import com.adidark.entity.PaymentEntity;
import com.adidark.enums.PaymentStatus;
import com.adidark.enums.StatusType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private LocalDate createdDate;
    private BigDecimal totalPrice;
    private String address;
    private List<OrderItemDTO> orderItemList;
    private PaymentStatus paymentStatus;
    private StatusType statusType;
}
