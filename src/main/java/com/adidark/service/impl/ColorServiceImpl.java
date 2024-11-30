package com.adidark.service.impl;

import com.adidark.converter.ColorDTOConverter;
import com.adidark.entity.ColorEntity;
import com.adidark.model.dto.ColorDTO;
import com.adidark.repository.ColorRepository;
import com.adidark.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    public ColorRepository colorRepository;

    @Autowired
    public ColorDTOConverter colorDTOConverter;


    @Override
    public List<ColorDTO> findAll() {

        List<ColorEntity> colorEntities = colorRepository.findAll();
        return colorDTOConverter.toColorDTOListFromEntities(colorEntities);
    }

    @Override
    public Page<ColorEntity> findAll(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }
}
