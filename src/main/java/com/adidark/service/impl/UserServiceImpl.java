package com.adidark.service.impl;

import com.adidark.converter.UserDTOConverter;
import com.adidark.entity.RoleEntity;
import com.adidark.entity.UserEntity;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;
import com.adidark.repository.RoleRepository;
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
    private RoleRepository roleRepository;

    @Autowired
    private UserDTOConverter userDTOConverter;
    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        Page<UserEntity> userEntityPage= userRepository.findAll(pageable);
        return userEntityPage.stream().map(item -> userDTOConverter.toUserDTO(item)).toList();
    }

    @Override
    public SuperClassDTO<UserDTO> searchUser(String query, Pageable pageable) {
        Page<UserEntity> userList=null;
        if(!StringUtils.isEmpty(query)){
            if(query.matches("\\d+")){
                userList=userRepository.findByTelephoneContainingIgnoreCase(query,pageable);
            }
            else {
                userList=userRepository.findByFirstNameOrLastNameContainingIgnoreCase(query,pageable);

            }
        }
        else {
            userList=userRepository.findAll(pageable);
        }
        SuperClassDTO<UserDTO> userDTO=new SuperClassDTO<>();
        userDTO.setTotalPage(userList.getTotalPages());
        userDTO.setCurrentPage(pageable.getPageNumber());
        userDTO.setSearchValue(query);
        userDTO.setItems(userList.stream().map(item -> userDTOConverter.toUserDTO(item)).toList());
        return userDTO;
    }

    @Override
    public SuperClassDTO<UserDTO> getUser(Integer id, Pageable pageable) {
        return null;
    }

    @Override
    public List<UserDTO> searchUser(String query) {
        return userRepository.findByFirstNameOrLastNameContainingIgnoreCase(query).stream().map(
                item ->userDTOConverter.toUserDTO(item)).toList();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        RoleEntity roleEntity = roleRepository.findById(userDTO.getId()).get();
        
        return null;
    }


}
