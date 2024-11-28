package com.adidark.repository;

import com.adidark.entity.ProductEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSizeEntity, Long> {
    Optional<ProductSizeEntity> findByProductEntityAndSizeEntity(ProductEntity productEntity, SizeEntity sizeEntity);
    Optional<ProductSizeEntity> findByProductEntityAndSizeEntity_Size(ProductEntity productEntity, BigDecimal size);
    void deleteByProductEntity(ProductEntity productEntity);
}
