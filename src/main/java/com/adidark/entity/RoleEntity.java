package com.adidark.entity;

import com.adidark.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role")
@DynamicInsert
@DynamicUpdate
public class RoleEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "roletype", nullable = false, unique = true)
    private RoleType roleType;

    @JsonManagedReference
    private List<UserEntity> userList;
}
