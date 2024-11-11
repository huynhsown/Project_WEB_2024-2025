package com.adidark.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "color")
@DynamicInsert
@DynamicUpdate
public class ColorEntity extends BaseEntity{
    @Column(name = "colorname")
    private String name;

    @ManyToMany(mappedBy = "colorList")
    private List<ProductEntity> productList;
}
