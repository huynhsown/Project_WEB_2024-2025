package com.adidark;

import com.adidark.entity.ProductEntity;
import com.adidark.entity.SupplierEntity;
import com.adidark.repository.ProductRepository;
import com.adidark.repository.SupplierRepository;
import com.adidark.service.SupplierService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class ProductLoadTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    @Test
    @Transactional
    @Rollback(value = false)
    void readData(){
        List<Long> supplierIds = Arrays.asList(1L, 2L, 3L);  // Example supplier IDs
        Pageable pageable = PageRequest.of(0, 10);  // Get the first 10 products

        // Page<ProductEntity> productsPage = productRepository.filterByMultipleSuppliers(supplierIds, pageable);



    }
}
