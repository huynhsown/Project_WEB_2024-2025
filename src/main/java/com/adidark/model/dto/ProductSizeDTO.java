package com.adidark.model.dto;

import com.adidark.entity.ProductEntity;
import com.adidark.entity.SizeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeDTO {

    private Long id;
    private Integer stock;
    private ProductDTO product;
    private SizeDTO size;
}
