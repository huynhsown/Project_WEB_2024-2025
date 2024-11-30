package com.adidark.controller.customer;

import com.adidark.entity.CartEntity;
import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.CartDTO;
import com.adidark.model.dto.ProductDTO;
import com.adidark.service.CartService;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test/customer")
public class TestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public CartDTO getUserCart(@RequestParam(required = true) Long userId) {
        return cartService.findByUserId(userId);
    }

    @GetMapping("/product")
    public ProductDTO getProduct(@RequestParam(required = true) Long productId) {
        return productService.findProductById(productId);
    }

}
