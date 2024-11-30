package com.adidark.service;

import com.adidark.entity.ColorEntity;
import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.ColorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ColorService {
    List<ColorDTO> findAll();
    Page<ColorEntity> findAll(Pageable pageable);
}
