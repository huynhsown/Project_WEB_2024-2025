package com.adidark.service.impl;

import com.adidark.converter.CartItemDTOConverter;
import com.adidark.converter.OrderDTOConverter;
import com.adidark.entity.*;
import com.adidark.enums.StatusType;
import com.adidark.model.dto.OrderDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.exception.DataNotFoundException;
import com.adidark.model.dto.CartItemDTO;
import com.adidark.model.dto.OrderDTO;
import com.adidark.repository.*;
import com.adidark.service.CartItemService;
import com.adidark.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.adidark.service.ProductSizeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    OrderDTOConverter orderDTOConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductSizeService productSizeService;

    @Autowired
    private CartItemDTOConverter cartItemDTOConverter;


    public OrderEntity addOrder(Long userId, String addressName, List<Long> orderItemIds) {
        // Lấy thông tin UserEntity từ userId
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Tạo mới AddressEntity từ addressName
        AddressEntity address = new AddressEntity();
        if (addressName != null && !addressName.isEmpty()) {
            address.setAddressName(addressName);
            address = addressRepository.save(address); // Lưu AddressEntity mới vào database
        }

        // Danh sách OrderItemEntity
        List<OrderItemEntity> orderItems = new ArrayList<>();
        if (orderItemIds != null && !orderItemIds.isEmpty()) {
            orderItems = orderItemRepository.findAllById(orderItemIds);

            // Kiểm tra nếu không tìm thấy sản phẩm nào từ danh sách ID
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

        // Liên kết OrderEntity với từng OrderItemEntity
        orderItems.forEach(item -> item.setOrderEntity(order));

        // Lưu OrderEntity vào database
        return orderRepository.save(order);
    }


    @Override
    public OrderItemEntity addOrderItem(Long orderId, Long productSizeId, Integer quantity, BigDecimal price) {
        return null;
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

        CartItemDTO cartItemDTO = cartItemDTOConverter.toCartItemDTO(cartItemEntity);

        // Tạo OrderItem mới
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setQuantity(cartItemDTO.getQuantity());

        // Áp dụng giá được giảm giá

        orderItemEntity.setPrice(cartItemDTO.getDiscountedPrice());

        // Cập nhật tổng giá
        orderItemEntity.setTotalPrice(orderItemEntity.getPrice().multiply(new BigDecimal(cartItemDTO.getQuantity())));
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
    public SuperClassDTO<OrderDTO> searchOrder(String query, Pageable pageable) {
        Page<OrderEntity> entityPage=null;
        if (!StringUtils.isEmpty(query)){
            entityPage=orderRepository.findByUserEntity_TelephoneContaining(query,pageable);
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

    @Override
    public List<Object[]> getOrders(int year) {
        return orderRepository.getTotalIncomeByMonth(year);
    }
}
