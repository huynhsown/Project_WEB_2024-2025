package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
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
    @JsonBackReference
    // @JsonIgnoreProperties({"productSizeList"})
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    @JsonBackReference
    private SizeEntity sizeEntity;
}
