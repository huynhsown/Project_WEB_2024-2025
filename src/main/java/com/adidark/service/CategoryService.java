package com.adidark.service;

import com.adidark.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
}
