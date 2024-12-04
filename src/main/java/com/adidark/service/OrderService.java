package com.adidark.service;

import com.adidark.entity.OrderEntity;
import com.adidark.entity.OrderItemEntity;
import com.adidark.enums.StatusType;
import com.adidark.model.dto.OrderDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.response.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface OrderService {
    OrderEntity addOrder(Long userId, Long addressId, List<Long> orderItemIds);
    OrderItemEntity addOrderItem(Long orderId, Long productSizeId, Integer quantity, BigDecimal price);

    SuperClassDTO<OrderDTO> searchOrder(String query, Pageable pageable);

    List<Object[]> searchOrder(String query);

    OrderDTO getOrder(Long id);

    List<StatusType> getAllStatus();

    ResponseDTO updateOrder(String orderJSON) throws JsonProcessingException;

    OrderEntity getOrderEntity(Long id);

}
