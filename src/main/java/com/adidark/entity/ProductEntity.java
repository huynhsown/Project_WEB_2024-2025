package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
@DynamicInsert
@DynamicUpdate
public class ProductEntity extends BaseEntity {

    @Column(name = "productname")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob
    private String description;

    @Column(name = "isdelete")
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonBackReference
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonBackReference
    private DiscountEntity discountEntity;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnoreProperties({"productList"})
    private SupplierEntity supplierEntity;

    @ManyToMany
    @JoinTable(
            name = "productcolor",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    @JsonManagedReference
    private List<ColorEntity> colorList;

    @OneToMany(mappedBy = "productEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductSizeEntity> productSizeList;

    @OneToMany(mappedBy = "productEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<ImageEntity> imageList;

    @OneToMany(mappedBy = "productEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItemEntity> orderItemList;

    @OneToMany(mappedBy = "productEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItemEntity> cartItemList;
}
