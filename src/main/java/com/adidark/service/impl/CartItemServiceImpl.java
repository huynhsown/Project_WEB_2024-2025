package com.adidark.service.impl;

import com.adidark.entity.CartItemEntity;
import com.adidark.repository.CartItemRepository;
import com.adidark.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public Optional<CartItemEntity> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public List<CartItemEntity> findAllById(List<Long> ids) {
        return cartItemRepository.findAllById(ids);
    }


    @Override
    public Optional<CartItemEntity> findByCartIdAndProductSizeId(Long cartId, Long productSizeId) {
        return cartItemRepository.findByCartEntity_IdAndProductSizeEntity_Id(cartId, productSizeId);
    }

    @Override
    public CartItemEntity save(CartItemEntity cartItemEntity) {
        return cartItemRepository.save(cartItemEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            cartItemRepository.deleteByIdCustom(id);
        } catch (Exception ex) {
            // Log toàn bộ exception để debug
            System.out.println(ex.getMessage());
            throw new RuntimeException("Delete failed", ex);
        }
    }


}
