package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "category")
@DynamicInsert
@DynamicUpdate
public class CategoryEntity extends BaseEntity{

    @Column(name = "categoryname", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private List<ProductEntity> productList;
}
