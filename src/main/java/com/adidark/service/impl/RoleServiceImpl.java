package com.adidark.service.impl;

import com.adidark.converter.RoleDTOConverter;
import com.adidark.entity.RoleEntity;
import com.adidark.model.dto.RoleDTO;
import com.adidark.repository.RoleRepository;
import com.adidark.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleDTOConverter roleDTOConverter;

    @Override
    public Optional<RoleEntity> getRoleName(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public List<RoleDTO> getAllRole() {
        return roleRepository.findAll().stream().map(item-> roleDTOConverter.toRoleDTO(item)).toList();
    }

    @Override
    public RoleEntity getRoleById(Long id) {
        return roleRepository.findById(id).get();
    }
}
