package com.adidark.service;

import com.adidark.model.dto.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProducts(Pageable pageable);
}
