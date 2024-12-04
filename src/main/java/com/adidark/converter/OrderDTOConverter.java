package com.adidark.converter;

import com.adidark.entity.OrderEntity;
import com.adidark.model.dto.OrderDTO;
import com.adidark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;


@Component
public class OrderDTOConverter {

    @Autowired
    UserService userService;
    public OrderDTO toOrderDTO(OrderEntity orderEntity){
        if (orderEntity == null){
            return null;
        }
        else {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setAddress_id(orderEntity.getAddressEntity().getId());
            orderDTO.setTotalPrice(orderEntity.getTotalPrice());
            orderDTO.setId(orderEntity.getId());
            orderDTO.setUser_id(orderEntity.getUserEntity().getId());
            orderDTO.setStatus(orderEntity.getStatus());
            orderDTO.setName(userService.getUser(orderDTO.getUser_id()).getFirstName()+" "+
                    userService.getUser(orderDTO.getUser_id()).getLastName());
            orderDTO.setCreateDate(orderEntity.getCreatedDate().toLocalDate());
            return orderDTO;
        }
    }
}
