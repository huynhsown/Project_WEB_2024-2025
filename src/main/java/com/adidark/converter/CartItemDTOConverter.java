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

    @Autowired
    private SizeDTOConverter sizeDTOConverter;

    public CartItemDTO toCartItemDTO(CartItemEntity cartItemEntity) {
        if (cartItemEntity == null) {
            return null;
        }

        CartItemDTO cartItemDTO = modelMapper.map(cartItemEntity, CartItemDTO.class);

        // Map nested ProductEntity to ProductDTO
        if (cartItemEntity.getProductSizeEntity() != null) {
            cartItemDTO.setProduct(productDTOConverter.toProductDTO(cartItemEntity.getProductSizeEntity().getProductEntity()));
            cartItemDTO.setSize(sizeDTOConverter.toSizeDTO(cartItemEntity.getProductSizeEntity().getSizeEntity()));
        }

        // Prevent circular reference by not setting the cart field in the DTO
        cartItemDTO.setCart(null);

        return cartItemDTO;
    }

}

