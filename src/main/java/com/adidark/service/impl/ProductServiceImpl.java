package com.adidark.service.impl;

import com.adidark.converter.ProductDTOConverter;
import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.repository.ProductRepository;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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
    public SuperClassDTO<ProductDTO> searchProducts(String query, Pageable pageable) {
        Page<ProductEntity> products = null;
        if(!StringUtils.isEmptyOrWhitespace(query)){
            products = productRepository.findByNameContainingIgnoreCase(query, pageable);
        }
        else{
            products = productRepository.findAll(pageable);
        }

        SuperClassDTO<ProductDTO> result = new SuperClassDTO<>();
        result.setTotalPage(products.getTotalPages());
        result.setCurrentPage(pageable.getPageNumber());
        result.setSearchValue(query);
        result.setItems(products.stream()
                .map(item -> productDTOConverter.toProductDTO(item))
                .toList());
        return result;
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
