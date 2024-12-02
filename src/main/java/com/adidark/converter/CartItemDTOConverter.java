package com.adidark.converter;

import com.adidark.entity.CartItemEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.model.dto.CartDTO;
import com.adidark.model.dto.CartItemDTO;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SizeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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

        CartItemDTO cartItemDTO = new CartItemDTO(); // modelMapper.map(cartItemEntity, CartItemDTO.class);
        cartItemDTO.setId(cartItemEntity.getId());
        cartItemDTO.setQuantity(cartItemEntity.getQuantity());
        cartItemDTO.setPrice(cartItemEntity.getPrice());
        cartItemDTO.setTotalPrice(cartItemEntity.getTotalPrice());

        // Map nested ProductEntity to ProductDTO
        if (cartItemEntity.getProductSizeEntity() != null) {
            ProductSizeEntity productSizeEntity = cartItemEntity.getProductSizeEntity();
            System.out.println("ProductSizeEntityID=" + productSizeEntity.getId());
            cartItemDTO.setProduct(productDTOConverter.toProductDTO(cartItemEntity.getProductSizeEntity().getProductEntity()));
            cartItemDTO.setSize(sizeDTOConverter.toSizeDTO(cartItemEntity.getProductSizeEntity().getSizeEntity()));
        }

        // Prevent circular reference by not setting the cart field in the DTO
        cartItemDTO.setCartId(cartItemEntity.getCartEntity().getId());
        cartItemDTO.setProductSizeId(cartItemEntity.getProductSizeEntity().getId());

        return cartItemDTO;
    }

}

