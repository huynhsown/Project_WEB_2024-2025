package com.adidark.model.dto;
import com.adidark.enums.PaymentStatus;
import com.adidark.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private StatusType status;
    private BigDecimal totalPrice;
    private Long user_id;
    private String name;
    private Long address_id;
    private LocalDate createDate;
    private LocalDate createdDate;
    private String address;
    private List<OrderItemDTO> orderItemList;
    private PaymentStatus paymentStatus;
    private StatusType statusType;
}
