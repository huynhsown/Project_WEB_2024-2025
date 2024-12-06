package com.adidark.service;

import com.adidark.entity.ProductSizeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductSizeService {
    ProductSizeEntity save(ProductSizeEntity productSizeEntity);
    Optional<ProductSizeEntity> findByProductIdAndSizeId(Long productId, Long sizeId);
}
