package com.adidark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class UserEntity extends BaseEntity{

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password", columnDefinition = "TEXT")
    @Lob
    private String passWord;

    @Column(name = "telephone", unique = true)
    private String telephone;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference
    private RoleEntity roleEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "userEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<OrderEntity> orderList;

    @OneToOne(mappedBy = "userEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CartEntity> cartList;
}
