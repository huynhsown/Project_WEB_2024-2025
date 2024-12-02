package com.adidark.service;

import com.adidark.entity.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderEntity addOrder(Long userId, Long addressId, List<Long> orderItemIds);
}
