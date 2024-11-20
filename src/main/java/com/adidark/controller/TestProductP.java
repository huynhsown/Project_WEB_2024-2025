package com.adidark.controller;

import com.adidark.entity.ProductEntity;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/test-product")
public class TestProductP {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ProductEntity getAllProducts() {
        Optional<ProductEntity> result = productService.findById(10L);
        if (result.isPresent()){
            return result.get();
        }
        return null;
    }


}
