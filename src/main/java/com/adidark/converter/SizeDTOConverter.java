package com.adidark.converter;

import com.adidark.entity.SizeEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.model.dto.SizeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class SizeDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public SizeEntity toSizeEntity(SizeDTO sizeDTO){
        return modelMapper.map(sizeDTO, SizeEntity.class);
    }

    public SizeDTO toSizeDTO(SizeEntity sizeEntity) {
        SizeDTO sizeDTO = modelMapper.map(sizeEntity, SizeDTO.class);  // Map basic properties using ModelMapper
        // If necessary, set additional properties that are not mapped directly
        // For example, you might want to calculate or retrieve the quantity from a related entity or list
        Integer quantity = sizeEntity.getProductSizeList().stream()
            .map(ProductSizeEntity::getStock)
            .reduce(0, Integer::sum);  // Sum up quantities from related ProductSizeEntities
        sizeDTO.setQuantity(quantity); // Set quantity field in SizeDTO
        return sizeDTO;
    }

    // Convert a list of SizeEntity to a list of SizeDTO
    public List<SizeDTO> toSizeDTOList(List<SizeEntity> sizeEntities) {
        return sizeEntities.stream()
            .map(this::toSizeDTO)  // Use the single size to DTO converter for each entity
            .collect(Collectors.toList());
    }

}
