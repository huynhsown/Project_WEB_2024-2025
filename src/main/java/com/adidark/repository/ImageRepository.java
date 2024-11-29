package com.adidark.repository;

import com.adidark.entity.ImageEntity;
import com.adidark.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    void deleteByProductEntity(ProductEntity productEntity);
}
