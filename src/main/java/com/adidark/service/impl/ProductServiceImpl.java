package com.adidark.service.impl;

import com.adidark.converter.ProductDTOConverter;
import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.ProductDTO;
import com.adidark.repository.ProductRepository;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDTOConverter productDTOConverter;

    @Override
    public List<ProductDTO> findAllProducts(Pageable pageable) {
        Page<ProductEntity> productEntityPage = productRepository.findAll(pageable);
        return productEntityPage.stream()
                .map(item -> productDTOConverter.toProductDTO(item))
                .toList();
    }

    @Override
    public List<ProductDTO> getSuggestions(String query) {
        query = query.trim();
        List<ProductEntity> productList = productRepository.findByNameContainingIgnoreCase(query);
        return productList.stream()
                .map(item -> productDTOConverter.toProductDTO(item))
                .toList();
    }
}
