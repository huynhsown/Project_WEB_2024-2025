package com.adidark.converter;

import com.adidark.entity.OrderEntity;
import com.adidark.entity.PaymentEntity;
import com.adidark.enums.PaymentStatus;
import com.adidark.enums.StatusType;
import com.adidark.model.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderDTOConverter {

    @Autowired
    private OrderItemDTOConverter orderItemDTOConverter;

    public OrderDTO toOrderDTO(OrderEntity orderEntity){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderEntity.getId());
        orderDTO.setAddress("KON TUM");
        orderDTO.setCreatedDate(orderEntity.getCreatedDate().toLocalDate());
        orderDTO.setTotalPrice(orderEntity.getTotalPrice());
        BigDecimal totalPrice = orderEntity.getPaymentList()
                .stream()
                .map(PaymentEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderDTO.setStatusType(orderEntity.getStatus());
        orderDTO.setPaymentStatus(
                orderEntity.getStatus().equals(StatusType.CANCELED)
                        ? PaymentStatus.CANCELED
                        : (totalPrice.compareTo(orderEntity.getTotalPrice()) >= 0 ? PaymentStatus.PAID : PaymentStatus.PENDING));
        orderDTO.setOrderItemList(orderEntity.getOrderItemList()
                .stream()
                .map(item -> orderItemDTOConverter.toOrderItemDTO(item))
                .toList());
        return orderDTO;
    }

}
