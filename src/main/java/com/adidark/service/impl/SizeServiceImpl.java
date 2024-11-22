package com.adidark.service.impl;

import com.adidark.entity.ColorEntity;
import com.adidark.entity.SizeEntity;
import com.adidark.repository.SizeRepository;
import com.adidark.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;


    @Override
    public List<SizeEntity> findAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Page<SizeEntity> findAll(Pageable pageable) {
        return sizeRepository.findAll(pageable);
    }
}
