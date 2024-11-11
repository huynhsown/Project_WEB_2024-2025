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
@Table(name = "address")
@DynamicInsert
@DynamicUpdate
public class AddressEntity extends BaseEntity{

    @OneToMany(mappedBy = "addressEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEntity> orderList;
}
