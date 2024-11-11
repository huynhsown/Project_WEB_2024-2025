package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cartitem")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class CartItemEntity extends BaseEntity{

    @Column(name = "quantity")
    @ColumnDefault("0")
    private Integer quantity;

    @Column(name = "price")
    @ColumnDefault("0")
    private BigDecimal price;

    @Column(name = "totalprice")
    private BigDecimal totalPrice;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;
}
