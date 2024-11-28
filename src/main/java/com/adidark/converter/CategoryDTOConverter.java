package com.adidark.converter;

import com.adidark.entity.CategoryEntity;
import com.adidark.entity.ColorEntity;
import com.adidark.entity.ImageEntity;
import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.CategoryDTO;
import com.adidark.model.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO toCategoryDTO(CategoryEntity categoryEntity){
        return modelMapper.map(categoryEntity, CategoryDTO.class);
    }
}
