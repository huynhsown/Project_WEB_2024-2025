package com.adidark.converter;

import com.adidark.entity.SizeEntity;
import com.adidark.model.dto.SizeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SizeDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public SizeEntity toSizeEntity(SizeDTO sizeDTO){
        return modelMapper.map(sizeDTO, SizeEntity.class);
    }

    public SizeDTO toSizeDTO(SizeEntity sizeEntity)
    {
        return modelMapper.map(sizeEntity,SizeDTO.class);
    }
}
