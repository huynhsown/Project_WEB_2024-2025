package com.adidark.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "paymentdetail")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class PaymentEntity extends BaseEntity{

    @Column(name = "amount")
    @ColumnDefault("0")
    private BigDecimal amount;

    @Column(name = "paymentmethod")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;

}
