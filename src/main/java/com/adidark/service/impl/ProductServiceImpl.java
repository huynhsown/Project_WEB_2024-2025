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
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(ProductEntity entity: productEntityPage){
            productDTOList.add(productDTOConverter.toProductDTO(entity));
        }
        return productDTOList;
    }
}
