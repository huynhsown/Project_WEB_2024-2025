package com.adidark.service.impl;

import com.adidark.entity.CartItemEntity;
import com.adidark.repository.CartItemRepository;
import com.adidark.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemRepository.findByCartEntity_IdAndProductEntity_Id(cartId, productId);
    }

    @Override
    public CartItemEntity save(CartItemEntity cartItemEntity) {
        return cartItemRepository.save(cartItemEntity);
    }


}
