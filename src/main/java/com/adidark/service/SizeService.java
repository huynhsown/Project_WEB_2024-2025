package com.adidark.service;

import com.adidark.entity.SizeEntity;
import com.adidark.model.dto.SizeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SizeService {
    List<SizeDTO> findAll();
    Page<SizeEntity> findAll(Pageable pageable);
}
