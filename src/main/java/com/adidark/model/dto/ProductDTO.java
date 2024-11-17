package com.adidark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String price;
    private String categoryName;
    private BigDecimal discountPercent;
    private String supplierName;
    private String colors;
    private String imageURL;
}
