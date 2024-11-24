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
import java.util.Optional;

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


    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(namePattern, pageable);
    }

    @Override
    public Page<ProductEntity> filterByMultipleCriteria(String namePattern, List<Long> supplierIds, List<Long> colorIds, List<Long> sizeIds, Pageable pageable) {
        return productRepository.filterByMultipleCriteria(namePattern, supplierIds, colorIds, sizeIds, pageable);
    }

    @Override
    public Page<ProductEntity> filterByMultipleCriteria2(String namePattern, Pageable pageable) {
        return productRepository.filterByMultipleCriteria2(namePattern, pageable);
    }

    @Override
    public Page<ProductEntity> filterByMultipleCriteria3(List<Long> supplierIds, Pageable pageable) {
        return productRepository.filterByMultipleCriteria3(supplierIds, pageable);
    }

    @Override
    public Page<ProductEntity> filterByMultipleCriteria4(List<Long> sizeIds, Pageable pageable) {
        return productRepository.filterByMultipleCriteria4(sizeIds, pageable);
    }

    @Override
    public Page<ProductEntity> filterByMultipleCriteria5(List<Long> colorIds, Pageable pageable) {
        return productRepository.filterByMultipleCriteria5(colorIds, pageable);
    }


    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }


}
