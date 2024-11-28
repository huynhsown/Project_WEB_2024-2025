package com.adidark.service.impl;

import com.adidark.converter.SupplierDTOConverter;
import com.adidark.entity.SupplierEntity;
import com.adidark.model.dto.SupplierDTO;
import com.adidark.repository.SupplierRepository;
import com.adidark.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierDTOConverter supplierDTOConverter;

    @Override
    public List<SupplierDTO> findAll() {
        List<SupplierEntity> supplierEntities = supplierRepository.findAll();
        return supplierEntities.stream()
                .map(item -> supplierDTOConverter.toSupplierDTO(item))
                .toList();
    }

    @Override
    public Page<SupplierEntity> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }
}
