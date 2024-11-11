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

    @OneToMany(mappedBy = "supplierEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductEntity> productList;
}
