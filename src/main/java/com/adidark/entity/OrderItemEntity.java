package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orderitem")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class OrderItemEntity extends BaseEntity{

    @Column(name = "quantity")
    @ColumnDefault("0")
    private Integer quantity;

    @Column(name = "price")
    @ColumnDefault("0")
    private BigDecimal price;

    @Column(name = "totalprice")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

}
