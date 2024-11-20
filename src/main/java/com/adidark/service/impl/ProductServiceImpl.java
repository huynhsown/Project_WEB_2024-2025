package com.adidark.service.impl;

import com.adidark.entity.ProductEntity;
import com.adidark.repository.ProductRepository;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(namePattern, pageable);
    }

    @Override
    public Page<ProductEntity> filterByMultipleSuppliers(String namePattern, List<Long> supplierIds, Pageable pageable) {
        // return productRepository.filterByMultipleSuppliers(namePattern, supplierIds, pageable);

        // Check if the supplierIds list is empty and set it to null if it is
        if (supplierIds != null && supplierIds.isEmpty()) {
            supplierIds = null; // Convert empty list to null to handle it in the repository
        }

        // Call the repository method with potentially modified supplierIds
        return productRepository.filterByMultipleSuppliers(namePattern, supplierIds, pageable);
    }


}
