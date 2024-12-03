package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Getter
@Setter
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
    @JoinColumn(name = "productsize_id")
    @JsonBackReference
    private ProductSizeEntity productSizeEntity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderEntity orderEntity;

}
