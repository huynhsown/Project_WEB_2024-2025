package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "address")
@DynamicInsert
@DynamicUpdate
public class AddressEntity extends BaseEntity {

    @Column(name = "addressName")
    private String addressName;

    @OneToMany(mappedBy = "addressEntity")
    @JsonManagedReference
    private List<OrderEntity> orderList;
}
