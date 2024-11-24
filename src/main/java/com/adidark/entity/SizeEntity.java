package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "size", nullable = false, unique = true)
    private BigDecimal size;
  
    @JsonManagedReference
    @OneToMany(mappedBy = "sizeEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ProductSizeEntity> productSizeList;
}
