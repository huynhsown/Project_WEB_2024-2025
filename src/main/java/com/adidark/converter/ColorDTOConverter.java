package com.adidark.converter;

import com.adidark.entity.ColorEntity;
import com.adidark.model.dto.ColorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColorDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ColorEntity toColorEntity(ColorDTO colorDTO){
        return modelMapper.map(colorDTO, ColorEntity.class);
    }

    public ColorEntity toColorEntity(String color){
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setName(color);
        return colorEntity;
    }

    public List<ColorDTO> toColorDTOList(List<String> colors){
        return colors.stream()
                .map(element -> new ColorDTO(element.trim()))
                .toList();
    }
}
