package com.adidark.service;

import com.adidark.entity.SizeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SizeService {
    List<SizeEntity> findAll();
    Page<SizeEntity> findAll(Pageable pageable);
}
