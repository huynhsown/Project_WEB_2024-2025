package com.adidark.service.impl;

import com.adidark.entity.SupplierEntity;
import com.adidark.repository.SupplierRepository;
import com.adidark.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.adidark.converter.SupplierDTOConverter;
import com.adidark.entity.SupplierEntity;
import com.adidark.model.dto.SupplierDTO;
import com.adidark.repository.SupplierRepository;
import com.adidark.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;


    @Override
    public Page<SupplierEntity> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }
  
    @Autowired
    private SupplierDTOConverter supplierDTOConverter;
}
