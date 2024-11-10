package com.adidark;

import com.adidark.entity.CategoryEntity;
import com.adidark.entity.DiscountEntity;
import com.adidark.entity.ProductEntity;
import com.adidark.entity.SupplierEntity;
import com.adidark.repository.CategoryRepository;
import com.adidark.repository.ProductRepository;
import com.adidark.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void test(){
// Giả sử các đối tượng CategoryEntity, DiscountEntity, và SupplierEntity đã tồn tại hoặc bạn có thể tạo mới chúng nếu cần
        CategoryEntity category = new CategoryEntity();
        category.setName("Electronics");

        DiscountEntity discount = new DiscountEntity();
        discount.setDiscountPercent(new BigDecimal(10));

        SupplierEntity supplier = new SupplierEntity();
        supplier.setName("Best Supplier");

// Tạo các đối tượng ProductEntity mới
        ProductEntity product1 = new ProductEntity();
        product1.setName("Smartphone");
        product1.setPrice(new BigDecimal("500.00"));
        product1.setDescription("A high-quality smartphone with advanced features.");
        product1.setCategoryEntity(category);
        product1.setSupplierEntity(supplier);

        ProductEntity product2 = new ProductEntity();
        product2.setName("Laptop");
        product2.setPrice(new BigDecimal("1200.00"));
        product2.setDescription("A powerful laptop for professionals.");
        product2.setCategoryEntity(category);
        product2.setSupplierEntity(supplier);

        ProductEntity product3 = new ProductEntity();
        product3.setName("Headphones");
        product3.setPrice(new BigDecimal("150.00"));
        product3.setDescription("Noise-canceling headphones with superior sound quality.");
        product3.setCategoryEntity(category);
        product3.setSupplierEntity(supplier);

        category.setProductList(List.of(product1, product2, product3));
        supplierRepository.save(supplier);
        categoryRepository.save(category);
    }

}
