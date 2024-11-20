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
import java.util.Optional;


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
        Optional<ProductEntity> res = productRepository.findById(10L);
        if (res.isPresent()){
            System.out.println(res.get().getName());
        }

    }
}
