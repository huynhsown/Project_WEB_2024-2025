package com.adidark.controller.customer;

import com.adidark.entity.ProductEntity;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test/customer/products")
public class TestController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-product")
    public Optional<ProductEntity> getProductById(@RequestParam Long productId) {

        return productService.findById(productId);
    }

    @GetMapping("/filter")
    public Page<ProductEntity> filterProducts(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(required = false) String namePattern,
                                              @RequestParam(required = false) List<Long> supplierIds,
                                              @RequestParam(required = false) List<Long> colorIds,
                                              @RequestParam(required = false) List<Long> sizeIds,
                                              @RequestParam(required = false, defaultValue = "priceAsc") String sort) {

        // Xử lý tham số rỗng thành null
        if (supplierIds != null && supplierIds.isEmpty()) {
            supplierIds = null;
        }
        if (colorIds != null && colorIds.isEmpty()) {
            colorIds = null;
        }
        if (sizeIds != null && sizeIds.isEmpty()) {
            sizeIds = null;
        }

        Pageable pageable;
        if ("priceAsc".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by("price").descending());
        }
        return productService.filterByMultipleCriteria(namePattern, supplierIds, colorIds, sizeIds, pageable);
    }


}
