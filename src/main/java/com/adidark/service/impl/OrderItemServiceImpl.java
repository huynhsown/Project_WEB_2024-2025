package com.adidark.service.impl;

import com.adidark.entity.OrderItemEntity;
import com.adidark.repository.OrderItemRepository;
import com.adidark.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Optional<OrderItemEntity> findById(Long id) {
        return orderItemRepository.findById(id);
    }

}
