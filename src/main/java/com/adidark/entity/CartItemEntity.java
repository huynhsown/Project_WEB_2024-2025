package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Getter
@Setter
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "productsize_id")
    @JsonBackReference
    private ProductSizeEntity productSizeEntity;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private CartEntity cartEntity;
}
