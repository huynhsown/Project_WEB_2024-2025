package com.adidark.service;

import com.adidark.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    Page<ProductEntity> findAll(Pageable pageable);
    Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable);
    Page<ProductEntity> filterByMultipleSuppliers(String namePattern, List<Long> supplierIds, Pageable pageable);
    Page<ProductEntity> filterByMultipleCriteria(String namePattern,
                                                 List<Long> supplierIds,
                                                 List<Long> colorIds,
                                                 List<Long> sizeIds,
                                                 Pageable pageable);
    Optional<ProductEntity> findById(Long id);
}
