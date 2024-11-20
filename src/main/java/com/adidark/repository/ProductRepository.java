package com.adidark.repository;

import com.adidark.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :namePattern, '%')) AND " +
        "(:supplierIds IS NULL OR p.supplierEntity.id IN :supplierIds)")
    Page<ProductEntity> filterByMultipleSuppliers(@Param("namePattern") String namePattern,
                                                  @Param("supplierIds") List<Long> supplierIds,
                                                  Pageable pageable);

}

