package com.adidark.converter;

import com.adidark.entity.RoleEntity;
import com.adidark.entity.UserEntity;
import com.adidark.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    public UserDTO toUserDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        if (userEntity.getCart() != null && userEntity.getCart().getCartItemList() != null) {
            userDTO.setCartSize(userEntity.getCart().getCartItemList().size());
        } else {
            userDTO.setCartSize(0);
        }

        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setTelephone(userEntity.getTelephone());
        userDTO.setUserName(userEntity.getUsername());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setConfirmPassword(""); // Hoặc để trống
        if (userEntity.getRoleEntity() != null) {
            userDTO.setRoleId(userEntity.getRoleEntity().getId());
        }
        return userDTO;
    }

    public UserEntity toUserEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setTelephone(userDTO.getTelephone());
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setPassWord(userDTO.getPassword());
        if (userDTO.getRoleId() != null) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(userDTO.getRoleId());
            userEntity.setRoleEntity(roleEntity);
        }
        return userEntity;
    }
}


