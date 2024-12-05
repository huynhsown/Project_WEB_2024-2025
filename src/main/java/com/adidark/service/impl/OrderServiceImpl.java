package com.adidark.service.impl;

import com.adidark.converter.OrderDTOConverter;
import com.adidark.entity.*;
import com.adidark.enums.StatusType;
import com.adidark.exception.DataNotFoundException;
import com.adidark.model.dto.OrderDTO;
import com.adidark.repository.*;
import com.adidark.service.CartItemService;
import com.adidark.service.OrderService;
import com.adidark.service.ProductSizeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductSizeService productSizeService;

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
    public OrderItemEntity addOrderItemToOrder(Long orderId, CartItemEntity cartItemEntity) {
        /**
         * QUAN TRỌNG
         * HÀM NÀY KHÔNG KIỂM TRA STOCK CÓ HỢP LỆ HAY KHÔNG
         * VIỆC KIỂM TRA STOCK HỢP LỆ DO HÀM KHÁC QUYẾT ĐỊNH
         * CartItem liên quan sẽ bị xóa tự động sau khi OrderItem được thêm
         * Tổng giá của Order và Stock sẽ được cập nhật
         * 
         */
        // Tìm Order từ orderId

        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        ProductSizeEntity productSizeEntity = productSizeRepository.findById(cartItemEntity.getProductSizeEntity().getId())
            .orElseThrow(() -> new RuntimeException("Product size not found"));



        // Tạo OrderItem mới
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setQuantity(cartItemEntity.getQuantity());
        orderItemEntity.setPrice(cartItemEntity.getPrice());
        orderItemEntity.setTotalPrice(cartItemEntity.getPrice().multiply(new BigDecimal(cartItemEntity.getQuantity())));
        orderItemEntity.setProductSizeEntity(productSizeEntity);
        orderItemEntity.setOrderEntity(orderEntity);

        // Lưu OrderItem vào cơ sở dữ liệu
        orderItemRepository.save(orderItemEntity);

        // Xóa CartItem liên quan
        cartItemService.delete(cartItemEntity.getId());

        // Cập nhật lại danh sách OrderItem của Order
        orderEntity.getOrderItemList().add(orderItemEntity);

        // Cập nhật lại tổng giá trị đơn hàng
        BigDecimal newTotalPrice = orderEntity.getOrderItemList().stream()
            .map(OrderItemEntity::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Cập nhật tồn kho
        productSizeEntity.setStock(productSizeEntity.getStock() - orderItemEntity.getQuantity());
        productSizeService.save(productSizeEntity);

        orderEntity.setTotalPrice(newTotalPrice);
        orderRepository.save(orderEntity);

        return orderItemEntity;
    }

    @Override
    public OrderDTO findById(Long id) {
        return orderDTOConverter.toOrderDTO(orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found")));
    }

    @Override
    public List<OrderDTO> findByUserName(String username) {
        UserEntity userEntity = userRepository.findByUserName(username);
        return userEntity.getOrderList()
                .stream()
                .map(item -> orderDTOConverter.toOrderDTO(item))
                .sorted(Comparator.comparing((OrderDTO order) -> order.getPaymentStatus().name().equals("PAID"))
                        .thenComparing(OrderDTO::getId, Comparator.reverseOrder()))
                .toList();
    }

    public boolean validProductSizeAndRequiredQuantity(Long productSizeId, Integer quantity) {
        ProductSizeEntity productSizeEntity = productSizeRepository.findById(productSizeId)
            .orElseThrow(() -> new IllegalArgumentException("ProductSize not found with ID: " + productSizeId));
        return (productSizeEntity.getStock() >= quantity);
    }
    
    public BigDecimal updateOrderTotalPrice(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        BigDecimal totalPrice = orderEntity.getOrderItemList().stream()
            .map(OrderItemEntity::getTotalPrice) // Lấy TotalPrice của từng OrderItem
            .reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng tất cả TotalPrice lại

        // Cập nhật TotalPrice của OrderEntity
        orderEntity.setTotalPrice(totalPrice);

        // Lưu OrderEntity
        orderRepository.save(orderEntity);
        return totalPrice;
    }

    /**
     * Trả về true nếu toàn bộ các CartItemEntities đều có thể chuyển thành orderItem và order được
     *
     * @param cartItemEntities  các cartItemEntities cần kiểm tra
     */
    public boolean validCartItemEntitiesForOrdering(List<CartItemEntity> cartItemEntities) {
    
        for (CartItemEntity cartItem : cartItemEntities) {
            boolean isValid = validProductSizeAndRequiredQuantity(
                cartItem.getProductSizeEntity().getId(),
                cartItem.getQuantity()
            );
            if (!isValid) {
                return false; // Trả về false ngay khi phát hiện điều kiện không hợp lệ
            }
        }
        return true; // Tất cả đều hợp lệ
    }
    
    public Map<String, Object> validCartItemEntitiesForOrdering2(List<CartItemEntity> cartItemEntities) {
        List<Long> invalidCartItemIds = new ArrayList<>();
        
        for (CartItemEntity cartItem : cartItemEntities) {
            boolean isValid = validProductSizeAndRequiredQuantity(
                cartItem.getProductSizeEntity().getId(),
                cartItem.getQuantity()
            );
            if (!isValid) {
                invalidCartItemIds.add(cartItem.getId());
            }
        }
        
        boolean isValidOverall = invalidCartItemIds.isEmpty();

        Map<String, Object> result = new HashMap<>();
        result.put("isValid", isValidOverall);
        result.put("invalidCartItemIds", invalidCartItemIds);

        return result;
    }

}
