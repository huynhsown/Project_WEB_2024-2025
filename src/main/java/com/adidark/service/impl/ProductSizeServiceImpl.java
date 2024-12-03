package com.adidark.service.impl;

import com.adidark.entity.ProductSizeEntity;
import com.adidark.repository.ProductSizeRepository;
import com.adidark.service.ProductService;
import com.adidark.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    @Autowired
    private ProductSizeRepository productSizeRepository;


    @Override
    public ProductSizeEntity save(ProductSizeEntity productSizeEntity) {
        return productSizeRepository.save(productSizeEntity);
    }

    @Override
    public Optional<ProductSizeEntity> findByProductIdAndSizeId(Long productId, Long sizeId) {
        return productSizeRepository.findByProductEntity_IdAndSizeEntity_Id(productId, sizeId);
    }
}
