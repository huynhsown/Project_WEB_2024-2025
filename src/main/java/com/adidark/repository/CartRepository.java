package com.adidark.repository;

import com.adidark.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserEntity_Id(Long userId);

    Optional<CartEntity> findByUserEntity_UserName(String username);
}
