package com.adidark.service.impl;

import com.adidark.converter.CartDTOConverter;
import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.model.dto.CartDTO;
import com.adidark.repository.CartRepository;
import com.adidark.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDTOConverter cartDTOConverter;

    @Override
    public Optional<CartEntity> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public CartDTO findByUserId(Long userId) {
        CartEntity cartEntity = cartRepository.findByUserEntity_Id(userId)
            .orElseThrow(() -> new NoSuchElementException("Cart not found for user ID: " + userId));
        return cartDTOConverter.toCartDTO(cartEntity);
    }

    @Override
    public Optional<CartEntity> findEntityByUserId(Long userId) {
        return cartRepository.findByUserEntity_Id(userId);
    }

    @Override
    public CartEntity save(CartEntity cartEntity) {
        return cartRepository.save(cartEntity);
    }


}
