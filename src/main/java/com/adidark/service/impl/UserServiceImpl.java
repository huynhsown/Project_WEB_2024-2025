package com.adidark.service.impl;

import com.adidark.converter.UserDTOConverter;
import com.adidark.entity.UserEntity;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;
import com.adidark.repository.UserRepository;
import com.adidark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTOConverter userDTOConverter;
    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        Page<UserEntity> userEntityPage= userRepository.findAll(pageable);
        return userEntityPage.stream().map(item -> userDTOConverter.toUserDTO(item)).toList();
    }

    @Override
    public SuperClassDTO<UserDTO> searchProducts(String query, Pageable pageable) {
        Page<UserEntity> userList=null;
        if(!StringUtils.isEmpty(query)){
            if(query.matches("\\d+")){
                userList=userRepository.findByTelephoneContainingIgnoreCase(query,pageable);
            }
            else {
                userList=userRepository.findByLastNameContainingIgnoreCase(query,pageable);
            }
        }
        else {
            userList=userRepository.findAll(pageable);
        }


        return null;
    }
}
