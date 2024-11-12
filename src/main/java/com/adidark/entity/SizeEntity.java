package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "size")
@DynamicInsert
@DynamicUpdate
public class SizeEntity extends BaseEntity{

    @Column(name = "size", nullable = false)
    private BigDecimal size;

    @OneToMany(mappedBy = "sizeEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSizeEntity> productSizeList;
}
