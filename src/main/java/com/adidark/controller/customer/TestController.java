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
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-product")
    public Optional<ProductEntity> getProductById(@RequestParam Long productId) {

        return productService.findById(productId);

    }


    @GetMapping("/filter5")
    public Page<ProductEntity> filterProducts5(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(required = false) List<Long> colorIds) {

        Pageable pageable = PageRequest.of(page, size);
        return productService.filterByMultipleCriteria5(colorIds, pageable);
    }

    @GetMapping("/filter4")
    public Page<ProductEntity> filterProducts4(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(required = false) List<Long> sizeIds) {

        Pageable pageable = PageRequest.of(page, size);
        return productService.filterByMultipleCriteria4(sizeIds, pageable);
    }

    @GetMapping("/filter3")
    public Page<ProductEntity> filterProducts3(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(required = false) List<Long> supplierIds) {

        Pageable pageable = PageRequest.of(page, size);
        return productService.filterByMultipleCriteria3(supplierIds, pageable);
    }

    @GetMapping("/filter2")
    public Page<ProductEntity> filterProducts2(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(required = false) String namePattern) {

        Pageable pageable = PageRequest.of(page, size);
        return productService.filterByMultipleCriteria2(namePattern, pageable);
    }



    @GetMapping("/filter")
    public Page<ProductEntity> filterProducts(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(required = false) String namePattern,
                                              @RequestParam(required = false) List<Long> supplierIds,
                                              @RequestParam(required = false) List<Long> colorIds,
                                              @RequestParam(required = false) List<Long> sizeIds,
                                              @RequestParam(required = false, defaultValue = "priceAsc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        return productService.filterByMultipleCriteria(namePattern, supplierIds, colorIds, sizeIds, pageable);
    }


}
