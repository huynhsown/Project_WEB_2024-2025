package com.adidark.service;

import com.adidark.entity.CartEntity;
import com.adidark.model.dto.CartDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CartService {
    void flush();
    Optional<CartEntity> findById(Long id);
    CartDTO findByUserId(Long userId);
    Optional<CartEntity> findEntityByUserId(Long userId);
    CartEntity save(CartEntity cartEntity);
    void updateCartTotalPrice(Long cartId);
    CartDTO findByUsername(String username);
    void addCartItemAndUpdateCart(Long cartId, Long productId, Long sizeId, Integer quantity);
    void deleteCartItemAndUpdateCart(Long cartItemId);
}
