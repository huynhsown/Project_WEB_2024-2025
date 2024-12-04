package com.adidark.model.dto;

import com.adidark.entity.AddressEntity;
import com.adidark.entity.UserEntity;
import com.adidark.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
