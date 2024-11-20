package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "discount")
@DynamicInsert
@DynamicUpdate
public class DiscountEntity extends BaseEntity{

    @Column(name = "discountname", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "discountpercent", nullable = false)
    @Range(min = 0, max = 100, message = "Discount percentage from 0 to 100")
    private BigDecimal discountPercent;

    @OneToMany(mappedBy = "discountEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProductEntity> productList;
}
