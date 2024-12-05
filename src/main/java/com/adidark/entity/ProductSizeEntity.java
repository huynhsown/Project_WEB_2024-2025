package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.util.List;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    // @JsonIgnoreProperties({"productSizeList"})
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    @JsonBackReference
    private SizeEntity sizeEntity;

    @OneToMany(mappedBy = "productSizeEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItemEntity> cartItemList;

    @OneToMany(mappedBy = "productSizeEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItemEntity> orderItemList;
}
