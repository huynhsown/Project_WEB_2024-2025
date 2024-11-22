package com.adidark.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "colorname")
    private String name;

    @ManyToMany(mappedBy = "colorList")
    @JsonBackReference
    private List<ProductEntity> productList;
}
