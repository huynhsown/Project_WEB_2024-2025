package com.adidark.service.impl;

import com.adidark.converter.OrderDTOConverter;
import com.adidark.entity.*;
import com.adidark.enums.StatusType;
import com.adidark.model.dto.OrderDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.repository.*;
import com.adidark.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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
    OrderDTOConverter orderDTOConverter;

    @Autowired
    private ObjectMapper objectMapper;


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
    public SuperClassDTO<OrderDTO> searchOrder(String query, Pageable pageable) {
        Page<OrderEntity> entityPage=null;
        if (!StringUtils.isEmpty(query)){
            entityPage=orderRepository.findByIdContainingIgnoreCase(query,pageable);
        }
        else {
            entityPage=orderRepository.findAll(pageable);
        }
        SuperClassDTO<OrderDTO> orderDTOSuperClassDTO=new SuperClassDTO<>();
        orderDTOSuperClassDTO.setCurrentPage(pageable.getPageNumber());
        orderDTOSuperClassDTO.setTotalPage(entityPage.getTotalPages());
        orderDTOSuperClassDTO.setSearchValue(query);
        orderDTOSuperClassDTO.setItems(entityPage.stream().map(item->orderDTOConverter.toOrderDTO(item)).toList());
        return orderDTOSuperClassDTO;
    }

    @Override
    public List<Object[]> searchOrder(String query) {
        return orderRepository.findOrdersByUserPhone(query);
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return  orderDTOConverter.toOrderDTO(orderRepository.findById(id).get());
    }

    @Override
    public List<StatusType> getAllStatus() {
        return orderRepository.findAllOrderStatuses();
    }

    @Override
    public ResponseDTO updateOrder(String orderJSON) throws JsonProcessingException {
        ResponseDTO responseDTO=new ResponseDTO();
        OrderDTO orderDTO=objectMapper.readValue(orderJSON,OrderDTO.class);
        try {
            OrderEntity orderEntity=orderRepository.findById(orderDTO.getId()).orElseThrow(()-> new RuntimeException("Không tồn tại đơn hàng này"));

            orderEntity.setStatus(orderDTO.getStatus());

            OrderEntity saveOrder=orderRepository.saveAndFlush(orderEntity);
            responseDTO.setMessage("Cập nhật sản phẩm thành công");
        } catch (RuntimeException e) {
            responseDTO.setMessage("Cập nhật thất bại");
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public OrderEntity getOrderEntity(Long id) {
        return orderRepository.findById(id).get();
    }
}
