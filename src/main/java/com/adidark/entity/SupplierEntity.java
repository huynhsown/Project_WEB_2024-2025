package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "supplier")
@DynamicInsert
@DynamicUpdate
public class SupplierEntity extends BaseEntity{

    @Column(name = "suppliername")
    private String name;

    @Column(name = "contactname")
    private String contactName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "supplierEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<ProductEntity> productList;
}
