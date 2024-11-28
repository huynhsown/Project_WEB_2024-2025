package com.adidark.service.impl;

import com.adidark.converter.CategoryDTOConverter;
import com.adidark.model.dto.CategoryDTO;
import com.adidark.repository.CategoryRepository;
import com.adidark.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDTOConverter categoryDTOConverter;

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(item -> categoryDTOConverter.toCategoryDTO(item))
                .toList();
    }
}
