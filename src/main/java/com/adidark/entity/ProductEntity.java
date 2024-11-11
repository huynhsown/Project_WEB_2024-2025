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
@Table(name = "product")
@DynamicInsert
@DynamicUpdate
public class ProductEntity extends BaseEntity {

    @Column(name = "productname")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    @Lob
    private String description;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SizeEntity> sizeList;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImageEntity> imageList;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private DiscountEntity discountEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplierEntity;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "productcolor",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<ColorEntity> colorList;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItemList;

}
