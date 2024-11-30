package com.adidark.converter;

import com.adidark.entity.CartEntity;
import com.adidark.model.dto.CartDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemDTOConverter cartItemDTOConverter;

    @Autowired
    private UserDTOConverter userDTOConverter; // To handle nested UserDTO conversion

    public CartDTO toCartDTO(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }

        CartDTO cartDTO = modelMapper.map(cartEntity, CartDTO.class);

        // Map nested CartItemEntity list to CartItemDTO list
        if (cartEntity.getCartItemList() != null) {
            cartDTO.setCartItemList(cartEntity.getCartItemList().stream()
                .map(cartItemDTOConverter::toCartItemDTO)
                .toList());
        }

        // Map nested UserEntity to UserDTO
        if (cartEntity.getUserEntity() != null) {
            cartDTO.setUser(userDTOConverter.toUserDTO(cartEntity.getUserEntity()));
        }

        return cartDTO;
    }

}

