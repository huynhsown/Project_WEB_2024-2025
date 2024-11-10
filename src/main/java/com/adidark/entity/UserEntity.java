package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class UserEntity extends BaseEntity{

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roleid", nullable = false)
    private RoleEntity roleEntity;
}
