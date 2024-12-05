package com.adidark.converter;

import com.adidark.entity.CartEntity;
import com.adidark.model.dto.CartDTO;
import com.adidark.model.dto.CartItemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CartDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemDTOConverter cartItemDTOConverter;

    @Autowired
    private UserDTOConverter userDTOConverter; // To handle nested UserDTO conversion

    private void configureMappings() {
        // Ánh xạ CartEntity -> CartDTO
        modelMapper.typeMap(CartEntity.class, CartDTO.class)
            .addMappings(mapper -> {
                mapper.map(CartEntity::getId, CartDTO::setId);
                mapper.map(CartEntity::getOrderDate, CartDTO::setOrderDate);
                mapper.map(CartEntity::getTotalPrice, CartDTO::setTotalPrice);
            });
    }

    public CartDTO toCartDTO(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cartEntity.getId());
        cartDTO.setOrderDate(cartEntity.getOrderDate());


        // Map nested CartItemEntity list to CartItemDTO list
        if (cartEntity.getCartItemList() != null) {
            cartDTO.setCartItemList(cartEntity.getCartItemList().stream()
                .map(cartItemDTOConverter::toCartItemDTO)
                .toList());
        }
        // Tính tổng giá trị đã giảm cho tất cả các mục trong giỏ hàng
        BigDecimal totalDiscountedPrice = cartEntity.getCartItemList().stream()
            .map(cartItemEntity -> {
                CartItemDTO cartItemDTO = cartItemDTOConverter.toCartItemDTO(cartItemEntity);
                return cartItemDTO.getTotalPrice();
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add); // Tính tổng discountedPrice của tất cả các mục

        // Cập nhật tổng giá trị cho CartDTO
        cartDTO.setTotalPrice(totalDiscountedPrice);

        // Map nested UserEntity to UserDTO
        if (cartEntity.getUserEntity() != null) {
            cartDTO.setUser(userDTOConverter.toUserDTO(cartEntity.getUserEntity()));
        }


        return cartDTO;
    }

}

