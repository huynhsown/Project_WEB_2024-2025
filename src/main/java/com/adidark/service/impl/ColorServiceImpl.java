package com.adidark.service.impl;

import com.adidark.entity.ColorEntity;
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
    private ColorRepository colorRepository;

    @Override
    public List<ColorEntity> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Page<ColorEntity> findAll(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }
}
