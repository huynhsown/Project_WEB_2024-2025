package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "productsize")
@DynamicInsert
@DynamicUpdate
public class ProductSizeEntity extends BaseEntity{

    @Column(name = "stock")
    @ColumnDefault("0")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private SizeEntity sizeEntity;
}
