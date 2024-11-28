package com.adidark.converter;

import com.adidark.entity.SupplierEntity;
import com.adidark.model.dto.SupplierDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public SupplierDTO toSupplierDTO(SupplierEntity supplierEntity){
        SupplierDTO supplierDTO = modelMapper.map(supplierEntity, SupplierDTO.class);
        return supplierDTO;
    }
}
