package com.adidark.model.dto;

import com.adidark.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private ProductDTO product;
    private SizeDTO size;
    private Long cartId;
    private Long productSizeId;
}
