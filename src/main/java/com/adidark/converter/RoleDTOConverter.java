package com.adidark.converter;

import com.adidark.entity.RoleEntity;
import com.adidark.model.dto.RoleDTO;
import org.hibernate.annotations.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public RoleDTO toRoleDTO(RoleEntity role){
        return modelMapper.map(role,RoleDTO.class);
    }
}
