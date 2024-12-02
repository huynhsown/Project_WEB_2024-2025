package com.adidark.converter;

import com.adidark.entity.UserEntity;
import com.adidark.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        return userDTO;
    }

    public UserEntity toUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, UserEntity.class);
    }

}
