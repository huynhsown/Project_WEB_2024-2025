package com.adidark;

import com.adidark.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class PhucProductTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    void testDeleteProduct(){
        productRepository.deleteById(2L);
    }

}
