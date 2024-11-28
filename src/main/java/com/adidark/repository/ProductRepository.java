package com.adidark.repository;

import com.adidark.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
    List<ProductEntity> findByNameContainingIgnoreCase(String namePattern);

    Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable);


    @Query("SELECT DISTINCT p FROM ProductEntity p " +
        "JOIN p.colorList c " +
        "JOIN p.productSizeList ps " +
        "WHERE (:namePattern IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :namePattern, '%'))) " +
        "AND (:supplierIds IS NULL OR p.supplierEntity.id IN :supplierIds) " +
        "AND (:colorIds IS NULL OR c.id IN :colorIds) " +
        "AND (:sizeIds IS NULL OR ps.sizeEntity.id IN :sizeIds) ")
    Page<ProductEntity> filterByMultipleCriteria(@Param("namePattern") String namePattern,
                                                 @Param("supplierIds") List<Long> supplierIds,
                                                 @Param("colorIds") List<Long> colorIds,
                                                 @Param("sizeIds") List<Long> sizeIds,
                                                 Pageable pageable);


    @Query("SELECT DISTINCT p FROM ProductEntity p " +
        "WHERE (:namePattern IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :namePattern, '%'))) ")
    Page<ProductEntity> filterByMultipleCriteria2(@Param("namePattern") String namePattern,
                                                 Pageable pageable);

    @Query("SELECT DISTINCT p FROM ProductEntity p " +
        "WHERE (:supplierIds IS NULL OR p.supplierEntity.id IN :supplierIds)")
    Page<ProductEntity> filterByMultipleCriteria3(@Param("supplierIds") List<Long> supplierIds,
                                                  Pageable pageable);

    @Query("SELECT DISTINCT p FROM ProductEntity p " +
        "JOIN p.productSizeList ps " +
        "WHERE (:sizeIds IS NULL OR ps.sizeEntity.id IN :sizeIds)")
    Page<ProductEntity> filterByMultipleCriteria4(@Param("sizeIds") List<Long> sizeIds,
                                                  Pageable pageable);

    @Query("SELECT DISTINCT p FROM ProductEntity p " +
        "JOIN p.colorList c " + // hơi đặc biệt tí, c là colorEntity, không phải colorList giống như quan hệ OneToMany giống như productSizeList khi join
        "WHERE (:colorIds IS NULL OR c.id IN :colorIds)")
    Page<ProductEntity> filterByMultipleCriteria5(@Param("colorIds") List<Long> colorIds,
                                                  Pageable pageable);

}
