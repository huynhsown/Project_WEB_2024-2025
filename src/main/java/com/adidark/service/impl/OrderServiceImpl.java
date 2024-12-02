package com.adidark.service.impl;

import com.adidark.entity.AddressEntity;
import com.adidark.entity.OrderEntity;
import com.adidark.entity.OrderItemEntity;
import com.adidark.entity.UserEntity;
import com.adidark.enums.StatusType;
import com.adidark.repository.AddressRepository;
import com.adidark.repository.OrderItemRepository;
import com.adidark.repository.OrderRepository;
import com.adidark.repository.UserRepository;
import com.adidark.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository; // Repository to fetch UserEntity

    @Autowired
    private AddressRepository addressRepository; // Repository to fetch AddressEntity

    @Autowired
    private OrderItemRepository orderItemRepository;

    /**
     * Create a new order with items and persist it.
     *
     * @param userId          ID of the user placing the order
     * @param addressId       ID of the address for the order (optional, can be null)
     * @param orderItemIds    List of order item IDs to include in the order
     * @return The saved OrderEntity
     */
    public OrderEntity addOrder(Long userId, Long addressId, List<Long> orderItemIds) {
        // Fetch UserEntity
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Fetch AddressEntity (optional)
        AddressEntity address = addressId != null ? addressRepository.findById(addressId)
            .orElse(null) : null;

        // Fetch OrderItems
        List<OrderItemEntity> orderItems = orderItemRepository.findAllById(orderItemIds);

        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item!");
        }

        // Calculate total price
        BigDecimal totalPrice = orderItems.stream()
            .map(OrderItemEntity::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create OrderEntity
        OrderEntity order = new OrderEntity();
        order.setUserEntity(user);
        order.setAddressEntity(address);
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);
        order.setStatus(StatusType.PENDING); // Default status

        // Link order to items
        orderItems.forEach(item -> item.setOrderEntity(order));

        // Save OrderEntity
        return orderRepository.save(order);
    }
}
