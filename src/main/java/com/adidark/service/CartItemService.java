package com.adidark.service;

import com.adidark.entity.CartItemEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CartItemService {

    Optional<CartItemEntity> findByCartIdAndProductSizeId(Long cartId, Long productSizeId);
    CartItemEntity save(CartItemEntity cartItemEntity);
    void delete(Long id);
}
