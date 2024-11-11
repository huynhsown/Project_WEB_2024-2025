package com.adidark;

import com.adidark.entity.test.*;
import com.adidark.repository.test.test_c_repository;
import com.adidark.repository.test.test_p_repository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ProductTest {

    @Autowired
    private test_p_repository testPRepository;

    @Autowired
    private test_c_repository testCRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void test() {
        // Tạo đối tượng test_p (sản phẩm)
        test_p product = new test_p();
        product.setName("Smartphone");
        product.setPrice(new BigDecimal("999.99"));

        // Tạo các đối tượng test_c (kích thước và số lượng tồn)
        test_c size1 = new test_c();
        size1.setSize(new BigDecimal("6.1"));
        size1.setStock(50); // Số lượng tồn

        test_c size2 = new test_c();
        size2.setSize(new BigDecimal("6.5"));
        size2.setStock(30); // Số lượng tồn

        product.setSizeList(List.of(size1, size2));

        size1.setProductEntity(product);
        size2.setProductEntity(product);

        testPRepository.save(product);

        testCRepository.deleteById(3L);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void del(){
        testCRepository.deleteById(2L);
    }
}
