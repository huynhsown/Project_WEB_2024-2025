package com.adidark.service.impl;

import com.adidark.converter.OrderDTOConverter;
import com.adidark.entity.*;
import com.adidark.enums.StatusType;
import com.adidark.exception.DataNotFoundException;
import com.adidark.model.dto.OrderDTO;
import com.adidark.repository.*;
import com.adidark.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private OrderDTOConverter orderDTOConverter;

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

        // Nếu không có orderItemIds, tạo đơn hàng mà không có sản phẩm
        List<OrderItemEntity> orderItems = new ArrayList<>();
        if (orderItemIds != null && !orderItemIds.isEmpty()) {
            // Fetch OrderItems nếu có orderItemIds
            orderItems = orderItemRepository.findAllById(orderItemIds);

            if (orderItems.isEmpty()) {
                throw new IllegalArgumentException("Order must contain at least one item!");
            }
        }

        // Tính tổng giá trị đơn hàng
        BigDecimal totalPrice = orderItems.stream()
            .map(OrderItemEntity::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo OrderEntity
        OrderEntity order = new OrderEntity();
        order.setUserEntity(user);
        order.setAddressEntity(address);
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);
        order.setStatus(StatusType.PENDING); // Trạng thái mặc định

        // Link order to items
        orderItems.forEach(item -> item.setOrderEntity(order));

        // Lưu OrderEntity
        return orderRepository.save(order);
    }

    // Thêm OrderItem cho Order đã tạo
    public OrderItemEntity addOrderItem(Long orderId, Long productSizeId, Integer quantity, BigDecimal price) {
        // Tìm Order từ orderId
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        ProductSizeEntity productSizeEntity = productSizeRepository.findById(productSizeId)
            .orElseThrow(() -> new RuntimeException("Product size not found"));

        // Tạo OrderItem mới
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setQuantity(quantity);
        orderItemEntity.setPrice(price);
        orderItemEntity.setTotalPrice(price.multiply(new BigDecimal(quantity)));
        orderItemEntity.setProductSizeEntity(productSizeEntity);
        orderItemEntity.setOrderEntity(orderEntity);

        // Lưu OrderItem vào cơ sở dữ liệu
        orderItemRepository.save(orderItemEntity);

        // Cập nhật lại danh sách OrderItem của Order
        orderEntity.getOrderItemList().add(orderItemEntity);
        // orderRepository.save(orderEntity); // Lưu lại Order với danh sách OrderItem đã cập nhật

        // Cập nhật lại tổng giá trị đơn hàng
        BigDecimal newTotalPrice = orderEntity.getOrderItemList().stream()
            .map(OrderItemEntity::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderEntity.setTotalPrice(newTotalPrice);
        orderRepository.save(orderEntity);

        return orderItemEntity;
    }

    @Override
    public OrderDTO findById(Long id) {
        return orderDTOConverter.toOrderDTO(orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found")));
    }

}
