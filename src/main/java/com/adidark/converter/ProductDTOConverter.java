package com.adidark.converter;

import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO toProductDTO(ProductEntity productEntity){
        ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
        return productDTO;
    }
}
