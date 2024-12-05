package com.adidark.repository;

import com.adidark.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByCartEntity_IdAndProductSizeEntity_Id(Long cartId, Long productSizeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CartItem c WHERE c.id = :id", nativeQuery = true)
    void deleteByIdCustom(@Param("id") Long id);
}
