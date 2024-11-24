package com.adidark.converter;

import com.adidark.entity.ColorEntity;
import com.adidark.entity.ImageEntity;
import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO toProductDTO(ProductEntity productEntity){
        ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
        productDTO.setColors(productEntity.getColorList().stream()
                .map(ColorEntity::getName)
                .toList());
        productDTO.setImageName(productEntity.getImageList().stream()
                .map(ImageEntity::getURL)
                .toList());
        return productDTO;
    }

    public ProductEntity toProductEntity(ProductDTO productDTO){
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        productEntity.setImageList(List.of());
        return productEntity;
    }
}
