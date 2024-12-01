package com.adidark.converter;

import com.adidark.entity.ColorEntity;
import com.adidark.model.dto.ColorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColorDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    // Convert a single ColorEntity to ColorDTO
    public ColorDTO toColorDTO(ColorEntity colorEntity) {
        return modelMapper.map(colorEntity, ColorDTO.class);  // Use ModelMapper to map properties
    }

    // Convert a list of ColorEntity to a list of ColorDTO
    public List<ColorDTO> toColorDTOListFromEntities(List<ColorEntity> colorEntities) {
        return colorEntities.stream()
            .map(this::toColorDTO)  // Convert each ColorEntity to ColorDTO
            .collect(Collectors.toList());
    }

    // Convert a ColorDTO to ColorEntity
    public ColorEntity toColorEntity(ColorDTO colorDTO){
        return modelMapper.map(colorDTO, ColorEntity.class);
    }

    // Convert a String (color name) to ColorEntity
    public ColorEntity toColorEntity(String color){
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setName(color);
        return colorEntity;
    }

    // Convert a List of Strings (color names) to a List of ColorDTO
    public List<ColorDTO> toColorDTOList(List<String> colors){
        return colors.stream()
            .map(element -> new ColorDTO(element.trim()))  // Trim each color name and convert to ColorDTO
            .collect(Collectors.toList());
    }
}

