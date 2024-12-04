package com.adidark.service.impl;

import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.entity.OrderEntity;
import com.adidark.entity.OrderItemEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.repository.OrderItemRepository;
import com.adidark.repository.OrderRepository;
import com.adidark.repository.ProductSizeRepository;
import com.adidark.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // @Autowired
    // private ProductSizeRepository productSizeRepository; // Để tìm ProductSizeEntity

    // @Autowired
    // private OrderRepository orderRepository;

    @Override
    public Optional<OrderItemEntity> findById(Long id) {
        return orderItemRepository.findById(id);
    }

    // /**
    //  * Thêm một OrderItem mới vào cơ sở dữ liệu.
    //  *
    //  * @param orderId        ID của OrderEntity
    //  * @param productSizeId  ID của ProductSizeEntity
    //  * @param quantity       Số lượng sản phẩm
    //  * @param price          Giá sản phẩm
    //  * @return OrderItemEntity đã được lưu
    //  */
    // public OrderItemEntity addOrderItem(Long orderId, Long productSizeId, Integer quantity, BigDecimal price) {
    //     /**
    //      * QUAN TRỌNG
    //      * HÀM NÀY KHÔNG KIỂM TRA STOCK CÓ HỢP LỆ HAY KHÔNG
    //      * VIỆC KIỂM TRA STOCK HỢP LỆ DO HÀM KHÁC QUYẾT ĐỊNH
    //      * 
    //      */
    //     // Tìm ProductSizeEntity theo ID
    //     ProductSizeEntity productSizeEntity = productSizeRepository.findById(productSizeId)
    //         .orElseThrow(() -> new IllegalArgumentException("ProductSize not found with ID: " + productSizeId));

    //     // Tìm OrderEntity theo ID
    //     OrderEntity orderEntity = orderRepository.findById(orderId)
    //         .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

    //     // Tính tổng giá
    //     BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

    //     // Tạo OrderItemEntity mới
    //     OrderItemEntity orderItemEntity = new OrderItemEntity();
    //     orderItemEntity.setQuantity(quantity);
    //     orderItemEntity.setPrice(price);
    //     orderItemEntity.setTotalPrice(totalPrice);
    //     orderItemEntity.setProductSizeEntity(productSizeEntity);
    //     orderItemEntity.setOrderEntity(orderEntity);

    //     // Lưu vào cơ sở dữ liệu
    //     return orderItemRepository.save(orderItemEntity);
    // }

    


}
