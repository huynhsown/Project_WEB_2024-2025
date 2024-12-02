package com.adidark.service;

import com.adidark.entity.RoleEntity;
import com.adidark.model.dto.RoleDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {
    Optional<RoleEntity> getRoleName(Long id);
    List<RoleDTO> getAllRole();
    RoleEntity getRoleById(Long id);
}
