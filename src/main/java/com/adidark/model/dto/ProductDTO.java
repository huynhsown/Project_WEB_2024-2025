package com.adidark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String price;
    private Long categoryId;
    private String categoryName;
    private BigDecimal discountPercent;
    private String description;
    private Long supplierId;
    private String supplierName;
    private List<String> colors;
    private List<String> imageName;
    private List<String> sizes;
}
