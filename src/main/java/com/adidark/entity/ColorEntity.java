package com.adidark.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "color")
@DynamicInsert
@DynamicUpdate
public class ColorEntity extends BaseEntity{
    @Column(name = "colorname", unique = true)
    private String name;

    @ManyToMany(mappedBy = "colorList")
    private List<ProductEntity> productList;
}
