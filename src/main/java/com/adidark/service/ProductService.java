package com.adidark.service;

import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProducts(Pageable pageable);
    SuperClassDTO<ProductDTO> searchProducts(String query, Pageable pageable);
    List<ProductDTO> getSuggestions(String query);

}
