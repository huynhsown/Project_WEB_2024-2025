package com.adidark.converter;

import com.adidark.entity.ColorEntity;
import com.adidark.entity.ImageEntity;
import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO toProductDTO(ProductEntity productEntity){
        ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
        productDTO.setColors(productEntity.getColorList()
                .stream()
                .map(ColorEntity::getName)
                .collect(Collectors.joining(", "))
        );
        productDTO.setImageURL(productEntity.getImageList().stream().findFirst().map(ImageEntity::getURL).orElse(null));
        return productDTO;
    }
}
