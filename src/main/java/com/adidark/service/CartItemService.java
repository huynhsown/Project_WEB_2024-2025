package com.adidark.service;

import com.adidark.entity.CartItemEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CartItemService {

    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);
    CartItemEntity save(CartItemEntity cartItemEntity);

}
