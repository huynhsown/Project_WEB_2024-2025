package com.adidark.service;

import com.adidark.entity.SupplierEntity;
import com.adidark.model.dto.SupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {
    List<SupplierDTO> findAll();
    // List<SupplierEntity> findAll();
    Page<SupplierEntity> findAll(Pageable pageable);
}
