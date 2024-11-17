package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@Setter
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
