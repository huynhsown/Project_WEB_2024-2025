package com.adidark.service;

import com.adidark.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Page<ProductEntity> findAll(Pageable pageable);
    Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable);
    Page<ProductEntity> filterByMultipleSuppliers(String namePattern, List<Long> supplierIds, Pageable pageable);
}
