package com.adidark.service;

import com.adidark.entity.CartItemEntity;
import com.adidark.entity.OrderEntity;
import com.adidark.entity.OrderItemEntity;
import com.adidark.model.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    OrderEntity addOrder(Long userId, Long addressId, List<Long> orderItemIds);
    OrderItemEntity addOrderItemToOrder(Long orderId, CartItemEntity cartItemEntity);
    OrderDTO findById(Long id);
    boolean validCartItemEntitiesForOrdering(List<CartItemEntity> cartItemEntities);
    boolean validProductSizeAndRequiredQuantity(Long productSizeId, Integer quantity);
    Map<String, Object> validCartItemEntitiesForOrdering2(List<CartItemEntity> cartItemEntities);
}
