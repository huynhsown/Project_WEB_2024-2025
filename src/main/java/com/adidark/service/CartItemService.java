package com.adidark.service;

import com.adidark.entity.CartItemEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartItemService {
    Optional<CartItemEntity> findById(Long id);
    List<CartItemEntity> findAllById(List<Long> ids);
    Optional<CartItemEntity> findByCartIdAndProductSizeId(Long cartId, Long productSizeId);
    CartItemEntity save(CartItemEntity cartItemEntity);
    void delete(Long id);
    void flush();
}
