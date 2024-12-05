package com.adidark.repository;

import com.adidark.entity.DiscountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
    Page<DiscountEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<DiscountEntity> findAllByOrderByDiscountPercentAsc(Pageable pageable);
    Page<DiscountEntity> findAllByOrderByDiscountPercentDesc(Pageable pageable);
}
