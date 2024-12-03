package com.adidark.service;

import com.adidark.entity.OrderEntity;
import com.adidark.entity.OrderItemEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface OrderService {
    OrderEntity addOrder(Long userId, Long addressId, List<Long> orderItemIds);
    OrderItemEntity addOrderItem(Long orderId, Long productSizeId, Integer quantity, BigDecimal price);
}
