package com.adidark.service;

import com.adidark.entity.SupplierEntity;
import com.adidark.model.dto.SupplierDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierService {
    List<SupplierDTO> findAll();
}
