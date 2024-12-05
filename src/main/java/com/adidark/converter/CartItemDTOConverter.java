package com.adidark.converter;

import com.adidark.entity.CartItemEntity;
import com.adidark.entity.DiscountEntity;
import com.adidark.entity.ProductEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.model.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartItemDTOConverter {

    @Autowired
    private ProductDTOConverter productDTOConverter; // To handle nested ProductDTO conversion

    @Autowired
    private SizeDTOConverter sizeDTOConverter;



    public CartItemDTO toCartItemDTO(CartItemEntity cartItemEntity) {
        if (cartItemEntity == null) {
            return null;
        }

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItemEntity.getId());
        cartItemDTO.setQuantity(cartItemEntity.getQuantity());
        cartItemDTO.setPrice(cartItemEntity.getPrice());

        // Apply discount, with null check for DiscountEntity
        BigDecimal discountPercent = BigDecimal.ZERO;  // Default to no discount

        if (cartItemEntity.getProductSizeEntity() != null) {
            ProductEntity productEntity = cartItemEntity.getProductSizeEntity().getProductEntity();

            if (productEntity != null && productEntity.getDiscountEntity() != null) {
                discountPercent = productEntity.getDiscountEntity().getDiscountPercent();
            }
        }

        // Calculate price after discount
        BigDecimal priceAfterDiscount = cartItemEntity.getPrice()
                .multiply(new BigDecimal(100).subtract(discountPercent)).
                divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        cartItemDTO.setDiscountedPrice(priceAfterDiscount);

        // Set total price considering the discount
        cartItemDTO.setTotalPrice(cartItemDTO.getDiscountedPrice().multiply(BigDecimal.valueOf(cartItemDTO.getQuantity())));

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

    public List<CartItemDTO> toCartItemDTOList(List<CartItemEntity> entities) {
        return entities.stream()
            .map(this::toCartItemDTO)
            .collect(Collectors.toList());
    }

}

