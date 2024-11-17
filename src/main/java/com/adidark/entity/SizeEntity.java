package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
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
