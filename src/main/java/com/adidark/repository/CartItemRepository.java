package com.adidark.repository;

import com.adidark.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByCartEntity_IdAndProductEntity_Id(Long cartId, Long productId);
}
