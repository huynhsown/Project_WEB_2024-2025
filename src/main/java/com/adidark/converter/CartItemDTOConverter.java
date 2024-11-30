package com.adidark.converter;

import com.adidark.entity.CartItemEntity;
import com.adidark.model.dto.CartItemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductDTOConverter productDTOConverter; // To handle nested ProductDTO conversion

    public CartItemDTO toCartItemDTO(CartItemEntity cartItemEntity) {
        if (cartItemEntity == null) {
            return null;
        }

        CartItemDTO cartItemDTO = modelMapper.map(cartItemEntity, CartItemDTO.class);

        // Map nested ProductEntity to ProductDTO
        if (cartItemEntity.getProductEntity() != null) {
            cartItemDTO.setProduct(productDTOConverter.toProductDTO(cartItemEntity.getProductEntity()));
        }

        // Prevent circular reference by not setting the cart field in the DTO
        cartItemDTO.setCart(null);

        return cartItemDTO;
    }

    public CartItemEntity toCartItemEntity(CartItemDTO cartItemDTO) {
        if (cartItemDTO == null) {
            return null;
        }

        CartItemEntity cartItemEntity = modelMapper.map(cartItemDTO, CartItemEntity.class);

        // Map nested ProductDTO to ProductEntity
        if (cartItemDTO.getProduct() != null) {
            cartItemEntity.setProductEntity(productDTOConverter.toProductEntity(cartItemDTO.getProduct()));
        }

        // Prevent circular reference by not setting the cartEntity field
        cartItemEntity.setCartEntity(null);

        return cartItemEntity;
    }
}

