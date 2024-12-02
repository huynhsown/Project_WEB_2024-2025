package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "address")
@DynamicInsert
@DynamicUpdate
public class AddressEntity extends BaseEntity {

    @OneToMany(mappedBy = "addressEntity")
    @JsonManagedReference
    private List<OrderEntity> orderList;
}
