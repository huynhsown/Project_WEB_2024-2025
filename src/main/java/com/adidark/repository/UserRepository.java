package com.adidark.repository;


import com.adidark.entity.RoleEntity;
import com.adidark.entity.UserEntity;
import com.adidark.model.dto.UserDTO;
import com.adidark.model.response.ResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findById(Integer id);

    Page<UserEntity> findByTelephoneContainingIgnoreCase(String query, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<UserEntity> findByFirstNameOrLastNameContainingIgnoreCase(String query, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<UserEntity> findByFirstNameOrLastNameContainingIgnoreCase(String query);

    List<UserEntity> findByTelephoneContainingIgnoreCase(String query);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.roleEntity = :role WHERE u.id = :id")
    int updateCustomer(@Param("role") RoleEntity role, @Param("id") Long id);

}
