package com.adidark.entity;

import com.adidark.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@Table(name = "role")
@DynamicInsert
@DynamicUpdate
public class RoleEntity extends BaseEntity{

    @Enumerated(EnumType.STRING) // Sử dụng Enum thay vì String
    @Column(name = "roletype", nullable = false, unique = true)
    private RoleType roleType;

    @OneToMany(mappedBy = "roleEntity", cascade = CascadeType.PERSIST, orphanRemoval = true) // Đảm bảo mối quan hệ rõ ràng
    private List<UserEntity> userList;
}
