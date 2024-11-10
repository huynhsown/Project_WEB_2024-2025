package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
@DynamicInsert
@DynamicUpdate
public class ProductEntity extends BaseEntity{

    @Column(name = "productname")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    @Lob
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryid")
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "discountid")
    private DiscountEntity discountEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplierid")
    private SupplierEntity supplierEntity;
}
