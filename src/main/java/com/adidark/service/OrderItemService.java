package com.adidark.service;

import com.adidark.entity.OrderItemEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrderItemService {
    Optional<OrderItemEntity> findById(Long id);
}
