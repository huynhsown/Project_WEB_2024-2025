package com.adidark.service;

import com.adidark.entity.CartItemEntity;
import com.adidark.entity.OrderEntity;
import com.adidark.entity.OrderItemEntity;
import com.adidark.enums.StatusType;
import com.adidark.model.dto.OrderDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.response.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import com.adidark.model.dto.OrderDTO;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    OrderItemEntity addOrderItemToOrder(Long orderId, CartItemEntity cartItemEntity);
    OrderDTO findById(Long id);
    List<OrderDTO> findByUserName(String username);
    boolean validCartItemEntitiesForOrdering(List<CartItemEntity> cartItemEntities);
    boolean validProductSizeAndRequiredQuantity(Long productSizeId, Integer quantity);
    Map<String, Object> validCartItemEntitiesForOrdering2(List<CartItemEntity> cartItemEntities);
    List<Object[]> getOrders(int year);
}
